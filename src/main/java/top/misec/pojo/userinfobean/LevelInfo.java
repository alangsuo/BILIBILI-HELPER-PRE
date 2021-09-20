package top.misec.pojo.userinfobean;

import lombok.Data;

/**
 * Auto-generated .
 *
 * @author Junzhou Liu
 * @since 2020/10/11 4:21
 */

@Data
public class LevelInfo {

    private int current_level;
    private int current_min;
    private int current_exp;
    private String next_exp;

    public int getNextExpAsInt() {
        if ("--".equals(next_exp)) {
            return current_exp;
        } else {
            return Integer.parseInt(next_exp);
        }
    }
}