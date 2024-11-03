package com.example.batchprocessing.domain.model;

public enum Orientation {
    N {
        @Override
        public Orientation turnLeft() { return W; }
        @Override
        public Orientation turnRight() { return E; }
        @Override
        public void advance(Position position) { position.setY(position.getY() + 1); }
    },
    E {
        @Override
        public Orientation turnLeft() { return N; }
        @Override
        public Orientation turnRight() { return S; }
        @Override
        public void advance(Position position) { position.setX(position.getX() + 1); }
    },
    S {
        @Override
        public Orientation turnLeft() { return E; }
        @Override
        public Orientation turnRight() { return W; }
        @Override
        public void advance(Position position) { position.setY(position.getY() - 1); }
    },
    W {
        @Override
        public Orientation turnLeft() { return S; }
        @Override
        public Orientation turnRight() { return N; }
        @Override
        public void advance(Position position) { position.setX(position.getX() - 1); }
    };

    public abstract Orientation turnLeft();
    public abstract Orientation turnRight();
    public abstract void advance(Position position);
}
