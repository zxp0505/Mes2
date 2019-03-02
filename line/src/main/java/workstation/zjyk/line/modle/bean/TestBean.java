package workstation.zjyk.line.modle.bean;

import java.util.List;

/**
 * Created by zjgz on 2017/11/2.
 */

public class TestBean {


    private String blog;
    private String name;
    private String url;
    private List<ProjectListBean> projectList;

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ProjectListBean> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectListBean> projectList) {
        this.projectList = projectList;
    }

    public static class ProjectListBean {
        /**
         * comment : Anroid 客户端标准协议网络框架。
         * id : 0
         * name : NoHttp
         * url : https://github.com/yanzhenjie/NoHttp
         */

        private String comment;
        private int id;
        private String name;
        private String url;

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
