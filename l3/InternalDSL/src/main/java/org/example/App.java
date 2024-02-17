package org.example;

import org.w3c.dom.Attr;

import java.util.*;
import java.util.stream.Collectors;

import static org.example.App.RelationType.*;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

    }


    public void lmao() {

        Builder builder = new AbstractBuilder() {

            final MutableEntity Person = entity(), Student = entity(), Teacher = entity(), Course = entity();
            @Override
            public void configure() {

                entity(Person, List.of(
                        attribute("age", 0, List.of(new MinMax(0, 135))),
                        attribute("name", String.class)
                ));

                entity(Student, List.of(attribute("Semester", Integer.class)), List.of(
                        relation(Student, INHERITS, Person)
                ));

                entity(Teacher, List.of(attribute("Semester", Integer.class)), List.of(
                        relation(Teacher, INHERITS, Person)
                ));

                entity(Course, List.of(attribute("Title", String.class)), List.of(
                        relation(Course, HAS_ONE, Teacher),
                        relation(Course, HAS_MANY, Student)
                ));



            }
        };

    }




    private interface Builder {
        MutableEntity entity();
        MutableEntity entity(MutableEntity entity, List<Attribute<?>> attributes);
        MutableEntity entity(MutableEntity entity, List<Attribute<?>> attributes, List<Relation> relations);


        Attribute<?> attribute(String title, Class<?> type);
        Attribute<?> attribute(String title, Class<?> type, List<Constraint<?>> constraints);
        <T> Attribute<T>  attribute(String title);
        <T> Attribute<T>  attribute(String title, T defaultValue);
        <T> Attribute<T>  attribute(String title, T defaultValue, List<Constraint<T>> constraints);


        Relation relation(MutableEntity entityA, RelationType relationType, MutableEntity entityB);

        void configure();

        void build();

    }

    static abstract class AbstractBuilder implements Builder {

        final List<Entity> entities;
        final List<Attribute<?>> attributes;

        @Override
        public MutableEntity entity() {
            return new MutableEntity();
        }

        @Override
        public MutableEntity entity(MutableEntity entity, List<Attribute<?>> attributes) {
            entity.attributes.clear();




            return entity;
        }

        @Override
        public MutableEntity entity(MutableEntity entity, List<Attribute<?>> attributes, List<Relation> relations) {
            return null;
        }

        @Override
        public Attribute<?> attribute(String title, Class<?> type) {
            return null;
        }

        @Override
        public Attribute<?> attribute(String title, Class<?> type, List<Constraint<?>> constraints) {
            return null;
        }

        @Override
        public <T> Attribute<T> attribute(String title) {
            return null;
        }

        @Override
        public <T> Attribute<T> attribute(String title, T defaultValue) {
            return null;
        }

        @Override
        public <T> Attribute<T> attribute(String title, T defaultValue, List<Constraint<T>> constraints) {
            return null;
        }

        @Override
        public Relation relation(MutableEntity entityA, RelationType relationType, MutableEntity entityB) {
            return null;
        }

        @Override
        public void build() {

        }
    }


    static abstract class Entity {
        String title;
        final Map<String, Attribute<?>> attributes = new HashMap<>();
        final Set<Relation> relations = new HashSet<>();

        public Entity() {}
        public Entity(String title) {
            this.title = title;
        }
        public Entity(String title, HashMap<String, Attribute<?>> attributes) {
            this.title = title;
            this.attributes.putAll(attributes);
        }
        public Entity(String title, HashMap<String, Attribute<?>> attributes, Set<Relation> relations) {
            this.title = title;
            this.attributes.putAll(attributes);
            this.relations.addAll(relations);
        }

    }

    static public class ImmutableEntity extends Entity {
        public ImmutableEntity(String title, MutableEntity entity) {

        }
    }

    static public class MutableEntity extends Entity {
        public MutableEntity() {

        }
        public HashMap<String, Attribute<?>> getAttributes() {
            return attributes;
        }

        public Set<Relation> getRelations() {
            return relations;
        }

        public ImmutableEntity asImmutable(String title) {
            return new ImmutableEntity(title, this);
        }
    }


    static class Attribute<T> {
        T value;
        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }

    static abstract class Constraint<T> {

    }

    static class MinMax extends Constraint<Integer> {
        final int min, max;
        MinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }
    }


    static class Relation {
        RelationType relationType;
        Entity entityA;
        Entity entityB;

    }

    enum RelationType {
        INHERITS,
        HAS_MANY,
        HAS_ONE
    }

}
