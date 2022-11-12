package Items;

    public abstract class Item {
        private String name;
        private double purchasingPrice;
        private double seelingPric;
        private double profit;
        private int availableItem;

        public Item(String name, double purchasingPrice, double seelingPric, int availableItem) {
            this.name = name;
            this.purchasingPrice = purchasingPrice;
            this.seelingPric = seelingPric;
            this.availableItem = availableItem;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPurchasingPrice(double purchasingPrice) {
            this.purchasingPrice = purchasingPrice;
        }

        public void setSeelingPric(double seelingPric) {
            this.seelingPric = seelingPric;
        }

        public void setProfit(double profit) {
            this.profit = profit;
        }

        public void setAvailableItem(int availableItem) {
            this.availableItem = availableItem;
        }

        public String getName() {
            return name;
        }

        public double getPurchasingPrice() {
            return purchasingPrice;
        }

        public double getSeelingPric() {
            return seelingPric;
        }

        public double getProfit() {
            return profit;
        }

        public int getAvailableItem() {
            return availableItem;
        }
        public abstract void calculateProfit(double purchasingPrice,double sellingPrice);
        @Override
        public String toString() {
            return "Item{" +
                    "name='" + name + '\'' +
                    ", purchasingPrice=" + purchasingPrice +
                    ", seelingPric=" + seelingPric +
                    ", profit=" + profit +
                    ", availableItem=" + availableItem +
                    '}';
        }
    }


