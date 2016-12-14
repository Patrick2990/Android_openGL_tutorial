package com.graphic;


abstract class GraphicEntity {

    boolean drawThis;

    abstract protected float[] getUvs();

    abstract protected float[] getModel();

    abstract public void mustDrawThis(boolean draw);

    abstract public boolean mustDrawThis();
}
