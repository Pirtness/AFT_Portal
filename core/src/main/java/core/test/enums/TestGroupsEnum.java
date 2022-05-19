package core.test.enums;

public enum TestGroupsEnum {
    QUALITY_GATE(1L),
    UI(2L),
    API(3L);

    private long id;

    TestGroupsEnum(long id) {this.id = id;}

    public long getId() {
        return this.id;
    }
}
