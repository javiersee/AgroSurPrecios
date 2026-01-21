package com.agrosurprecios.agrosurprecios_api.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/whatsapp")
public class WhatsappWebhookController {
    private static final String VERIFY_TOKEN = "agrosurprecios_verify";

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

    // 2️⃣ Recepción de mensajes
    @PostMapping("/webhook")
    public ResponseEntity<String> recibirMensaje(@RequestBody String payload) {

        System.out.println("📩 Mensaje recibido desde WhatsApp:");
        System.out.println(payload);

        return ResponseEntity.ok("EVENT_RECEIVED");
    }
}
