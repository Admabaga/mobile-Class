package com.example.myapplication.Network;

public class RespuestaError {
        private String timestamp;
        private int status;
        private String error;
        private String message;  // Este es el campo donde está tu mensaje de error
        private String path;

        // Getters y setters
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        // Puedes agregar getters y setters para los otros campos si los necesitas

}
