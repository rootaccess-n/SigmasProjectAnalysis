package rest.api.constellationsreservations.Enums;

public enum Subscription {


        STANDARD(10, 5),
        PLUS(20, 10),
        PREMIUM(50, 25);

        private final int a;
        private final int b;

      private Subscription(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public int getA() {
            return a;
        }

        public int getB() {
            return b;
        }




}

