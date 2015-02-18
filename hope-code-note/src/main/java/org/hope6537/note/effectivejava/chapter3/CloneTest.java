package org.hope6537.note.effectivejava.chapter3;

/**
 * 但是实际上我们应该避免Clone方法
 */
public class CloneTest {
}

/**
 * 在这里我们新建了一个HashTable
 */
class HashTable implements Cloneable {

    private Entry[] buckets = {};

    /**
     * 但是这样的clone会产生克隆对象和原始对象的不确定行为，所以为了修正
     * 我们必须单独的拷贝并组成每个bucket的链表
     *
     * @return
     */
   /* @Override
    protected HashTable clone() {
        try {
            HashTable result = (HashTable) super.clone();
            //克隆散列数组
            result.buckets = buckets.clone();
            return result;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }*/
    @Override
    protected Object clone() {
        try {
            HashTable result = (HashTable) super.clone();
            //生成克隆的bucket容器的具体项
            result.buckets = new Entry[buckets.length];
            //深度迭代克隆
            for (int i = 0; i < buckets.length; i++) {
                if (buckets[i] == null) {
                    result.buckets[i] = buckets[i].deepCopy();
                }
            }
            return result;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class Entry {
        final Object key;
        Object value;
        Entry entryNext;

        Entry(Object key, Object value, Entry entryNext) {
            this.key = key;
            this.value = value;
            this.entryNext = entryNext;
        }

        /**
         * 深度克隆
         * 但是这里会产生一个小问题，那就是递归的性能开销
         *
         * @return
         */
        Entry deepCopy() {
            return new Entry(key, value, entryNext == null ? null : entryNext.deepCopy());
        }

        Entry deepCopyHighSpeed() {
            Entry entry = new Entry(key, value, entryNext);
            for (Entry p = entry; p.entryNext != null; p = p.entryNext) {
                p.entryNext = new Entry(p.entryNext.key, p.entryNext.value, p.entryNext.entryNext);
            }
            return entry;
        }


    }
}