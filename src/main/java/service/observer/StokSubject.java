package service.observer;

import model.UrunModel;

public interface StokSubject {

	void addObserver(StokObserver observer);

	void removeObserve(StokObserver observer);

	void notifyObservers();
}
