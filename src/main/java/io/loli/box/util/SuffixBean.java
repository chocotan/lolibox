package io.loli.box.util;

public class SuffixBean {
    private String suffix;

    public SuffixBean(String suffix) {
        this.suffix = suffix;
    }

    public String toString() {
        return suffix.toLowerCase();
    }

    public int hashCode() {
        return suffix.toLowerCase().hashCode();
    }

    public boolean equals(Object target) {
        if (target != null && target instanceof SuffixBean) {
            SuffixBean suf = (SuffixBean) target;
            if (this.suffix.equalsIgnoreCase(suf.suffix)) {
                return true;
            } else {
                if (this.suffix.equalsIgnoreCase("jpg") && suf.suffix.equalsIgnoreCase("jpeg")) {
                    return true;
                }
                if (this.suffix.equalsIgnoreCase("jpeg") && suf.suffix.equalsIgnoreCase("jpg")) {
                    return true;
                }
                return false;
            }
        } else {
            return false;
        }

    }
}