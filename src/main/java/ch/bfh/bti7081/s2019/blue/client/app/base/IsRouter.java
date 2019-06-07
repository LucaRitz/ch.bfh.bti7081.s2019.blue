package ch.bfh.bti7081.s2019.blue.client.app.base;

public interface IsRouter {
    <C, T> void navigate(Class<C> navigationTarget, T parameter);

    <C> void navigate(Class<C> navigationTarget);
}
