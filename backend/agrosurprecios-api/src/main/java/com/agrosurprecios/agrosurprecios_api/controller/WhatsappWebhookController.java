package com.agrosurprecios.agrosurprecios_api.controller;

import com.agrosurprecios.agrosurprecios_api.service.PrecioArvejaService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/whatsapp")
public class WhatsappWebhookController {
    private static final String VERIFY_TOKEN = "agrosurprecios_verify";

    private final ObjectMapper objectMapper;
    private final PrecioArvejaService precioArvejaService;

    public WhatsappWebhookController(ObjectMapper objectMapper, PrecioArvejaService precioArvejaService) {
        this.objectMapper = objectMapper;
        this.precioArvejaService = precioArvejaService;
    }

    // 1️⃣ Verificación del webhook (Meta)
    @GetMapping("/webhook")
    public ResponseEntity<String> verificarWebhook(
            @RequestParam("hub.mode") String mode,
            @RequestParam("hub.verify_token") String token,
            @RequestParam("hub.challenge") String challenge
    ) {
        if ("subscribe".equals(mode) && VERIFY_TOKEN.equals(token)) {
            return ResponseEntity.ok(challenge);
        }
        return ResponseEntity.status(403).body("Token inválido");
    }

    // 2️⃣ Recepción de mensajes desde WhatsApp Cloud API
    @PostMapping("/webhook")
    public ResponseEntity<String> recibirMensaje(@RequestBody String payload) {
        System.out.println("📩 Mensaje recibido desde WhatsApp:");
        System.out.println(payload);

        try {
            JsonNode root = objectMapper.readTree(payload);

            JsonNode messagesNode = root.path("entry").isArray() && root.path("entry").size() > 0
                    ? root.path("entry").get(0)
                    : null;

            if (messagesNode != null) {
                JsonNode changes = messagesNode.path("changes");
                if (changes.isArray() && changes.size() > 0) {
                    JsonNode value = changes.get(0).path("value");
                    JsonNode messages = value.path("messages");

                    if (messages.isArray() && messages.size() > 0) {
                        JsonNode message = messages.get(0);
                        String type = message.path("type").asText("");

                        if ("text".equals(type)) {
                            String body = message.path("text").path("body").asText("");
                            Double precio = extraerPrecio(body);

                            if (precio != null) {
                                precioArvejaService.guardarPrecio(precio);
                                System.out.println("✅ Precio guardado desde WhatsApp: " + precio);
                                return ResponseEntity.ok("PRECIO_REGISTRADO");
                            } else {
                                System.out.println("⚠️ No se encontró un número de precio en el mensaje.");
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("❌ Error procesando payload de WhatsApp: " + e.getMessage());
        }

        return ResponseEntity.ok("EVENT_RECEIVED");
    }

    /**
     * Extrae el primer número del texto del mensaje.
     * Ejemplos válidos:
     *  - "arveja 130000 bulto"
     *  - "precio: 125.500 alberja"
     */
    private Double extraerPrecio(String texto) {
        if (texto == null) {
            return null;
        }

        Pattern pattern = Pattern.compile("(\\d+[\\d\\.]*,?\\d*)");
        Matcher matcher = pattern.matcher(texto.replace(" ", ""));

        if (matcher.find()) {
            String numero = matcher.group(1)
                    .replace(".", "")
                    .replace(",", ".");
            try {
                return Double.parseDouble(numero);
            } catch (NumberFormatException ignored) {
                return null;
            }
        }
        return null;
    }
}
