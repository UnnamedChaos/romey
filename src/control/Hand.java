package control;

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import model.Brick;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

class Hand implements ObservableList<Brick> {
    @Override
    public void addListener(ListChangeListener<? super Brick> listener) {

    }

    @Override
    public void removeListener(ListChangeListener<? super Brick> listener) {

    }

    @Override
    public boolean addAll(Brick... elements) {
        return false;
    }

    @Override
    public boolean setAll(Brick... elements) {
        return false;
    }

    @Override
    public boolean setAll(Collection<? extends Brick> col) {
        return false;
    }

    @Override
    public boolean removeAll(Brick... elements) {
        return false;
    }

    @Override
    public boolean retainAll(Brick... elements) {
        return false;
    }

    @Override
    public void remove(int from, int to) {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<Brick> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(Brick brick) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends Brick> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Brick> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Brick get(int index) {
        return null;
    }

    @Override
    public Brick set(int index, Brick element) {
        return null;
    }

    @Override
    public void add(int index, Brick element) {

    }

    @Override
    public Brick remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<Brick> listIterator() {
        return null;
    }

    @Override
    public ListIterator<Brick> listIterator(int index) {
        return null;
    }

    @Override
    public List<Brick> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public void addListener(InvalidationListener listener) {

    }

    @Override
    public void removeListener(InvalidationListener listener) {

    }
}
