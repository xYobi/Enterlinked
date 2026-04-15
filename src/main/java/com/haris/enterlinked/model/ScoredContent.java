package com.haris.enterlinked.model;

    public class ScoredContent {
        private Content content;
        private int score;

        public ScoredContent(Content content, int score){
            this.content = content;
            this.score = score;
        }


        public Content getContent() {
            return content;
        }

        public int getScore() {
            return score;
        }

}
