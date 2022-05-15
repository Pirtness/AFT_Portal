package core.test.enums;

public enum TestGroupsEnum {
    ALL(1L),
    REGRESS(2L),
    ID_APP(3L),
    SOWA(4L),
    QUALITY_GATE(5L),
    REGRESS_FIRST_PRIORITY(6L);

    private long id;

    TestGroupsEnum(long id) {this.id = id;}

    public long getId() {
        return this.id;
    }
}
