package top.misec.task;

/**
 * Task Interface.
 *
 * @author @Kurenai
 * @since 2020-11-22 5:22
 */
public interface Task {

    /**
     * task接口.
     */
    void run();

    /**
     * 任务名.
     *
     * @return taskName
     */
    String getName();

}