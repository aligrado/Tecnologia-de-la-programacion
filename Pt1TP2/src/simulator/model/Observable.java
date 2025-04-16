package simulator.model;

public interface Observable<T> {//No estoy segura de si esta interfaz se hace en este paquete
	void addObserver(T o);
	void removeObserver(T o);
}
