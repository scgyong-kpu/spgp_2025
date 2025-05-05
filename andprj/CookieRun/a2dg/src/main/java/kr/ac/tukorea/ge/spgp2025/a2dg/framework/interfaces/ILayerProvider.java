package kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces;

public interface ILayerProvider<E extends Enum<E>> extends IGameObject {
    public E getLayer();
}
