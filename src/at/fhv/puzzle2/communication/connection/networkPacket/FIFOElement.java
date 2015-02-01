package at.fhv.puzzle2.communication.connection.networkPacket;

import java.util.concurrent.atomic.AtomicLong;

public class FIFOElement<E extends Comparable<? super E>> implements Comparable<FIFOElement<E>> {
    private static final AtomicLong _sequence = new AtomicLong(0);

    private int _sequenceNumber;
    private E _element;

    public FIFOElement(E element) {
        _element = element;

        _sequence.getAndIncrement();
    }

    public E getElement() {
        return _element;
    }

    @Override
    public int compareTo(FIFOElement<E> element) {
        int compareResult = this._element.compareTo(element._element);

        if(compareResult == 0) {
            return this._sequenceNumber < element._sequenceNumber ? -1 : 1;
        }

        return compareResult;
    }
}
