package org.hope6537.wonderland.sail.autocomplete.model;


import org.hope6537.wonderland.sail.util.Cn2Spell;

public class AutoComplete {

    private String id;//hidden input的value
    private String name;//text input的value
    private String spell;//拼音全写
    private String firstSpell;//拼音首字母

    private String keyword;//查询时候的关键字
    private boolean isShowAll;//是否显示全部结果
    private String flag;//查询类别

    public AutoComplete() {
    }

    public AutoComplete(String id, String name) {
        this.id = id;
        this.name = name;
        setSpellAndFirstSpellByName();
    }

    public void setSpellAndFirstSpellByName() {
        setSpell(Cn2Spell.converterToSpell(getName()));
        setFirstSpell(Cn2Spell.converterToFirstSpell(getName()));
    }

    public boolean contain(String keyword) {
        return getSpell().contains(keyword) || getFirstSpell().contains(keyword) || getName().toLowerCase().contains(keyword);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public String getFirstSpell() {
        return firstSpell;
    }

    public void setFirstSpell(String firstSpell) {
        this.firstSpell = firstSpell;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword.toLowerCase();
    }

    public boolean isShowAll() {
        return isShowAll;
    }

    public void setIsShowAll(boolean showAll) {
        isShowAll = showAll;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}

