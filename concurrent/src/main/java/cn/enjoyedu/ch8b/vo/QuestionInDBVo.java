package cn.enjoyedu.ch8b.vo;

/**
 *类说明：题目实体类
 */
public class QuestionInDBVo {
    //题目id
    private final int id;
    //题目详情，平均长度700字节
    private final String detail;

    /**
     * 为什么不使用MD5，md5尺寸会更小，比sha哈希冲突的概率大 
     */
    //题目sha摘要（题目可能会更改，引入sha来解决题目过期的问题，每次修改题目之后都会更新sha）
    private final String sha;

    public QuestionInDBVo(int id, String detail, String sha) {
        this.id = id;
        this.detail = detail;
        this.sha = sha;
    }

    public int getId() {
        return id;
    }

    public String getDetail() {
        return detail;
    }

    public String getSha() {
        return sha;
    }
}
