package org.hope6537.note.design.factory.human;

public class HumanFactory extends AbstractHumanFactory {


    @SuppressWarnings("unchecked")
    @Override
    public <T extends Human> T createHuman(Class<T> cls) {

        Human human = null;

        try {
            human = (T) Class.forName(cls.getName()).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) human;
    }
}
