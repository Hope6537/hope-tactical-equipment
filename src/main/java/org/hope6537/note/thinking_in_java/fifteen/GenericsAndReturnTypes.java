package org.hope6537.note.thinking_in_java.fifteen;

interface GenericGetter<T extends GenericGetter<T>> {
	T get();
}

interface Getter extends GenericGetter<Getter> {

}

public class GenericsAndReturnTypes {
	void test(Getter g){
		Getter rGetter = g.get();
		//卧槽这他妈是啥意思？
		GenericGetter gg = g.get();
	}
}
