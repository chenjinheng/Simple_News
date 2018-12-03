package com.example.simple_news.domain;

import java.util.List;



public class PhotosMenuDetailPagerBean {



    private int retcode;


    private DataEntity data;

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public int getRetcode() {
        return retcode;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private String title;
        private String countcommenturl;
        private String more;
        private List<?> topic;



        private List<NewsEntity> news;

        public void setTitle(String title) {
            this.title = title;
        }

        public void setCountcommenturl(String countcommenturl) {
            this.countcommenturl = countcommenturl;
        }

        public void setMore(String more) {
            this.more = more;
        }

        public void setTopic(List<?> topic) {
            this.topic = topic;
        }

        public void setNews(List<NewsEntity> news) {
            this.news = news;
        }

        public String getTitle() {
            return title;
        }

        public String getCountcommenturl() {
            return countcommenturl;
        }

        public String getMore() {
            return more;
        }

        public List<?> getTopic() {
            return topic;
        }

        public List<NewsEntity> getNews() {
            return news;
        }

        public static class NewsEntity {
            private int id;
            private String title;
            private String url;
            private String listimage;
            private String smallimage;
            private String largeimage;
            private String pubdate;
            private boolean comment;
            private String commenturl;
            private String type;
            private String commentlist;

            public void setId(int id) {
                this.id = id;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public void setListimage(String listimage) {
                this.listimage = listimage;
            }

            public void setSmallimage(String smallimage) {
                this.smallimage = smallimage;
            }

            public void setLargeimage(String largeimage) {
                this.largeimage = largeimage;
            }

            public void setPubdate(String pubdate) {
                this.pubdate = pubdate;
            }

            public void setComment(boolean comment) {
                this.comment = comment;
            }

            public void setCommenturl(String commenturl) {
                this.commenturl = commenturl;
            }

            public void setType(String type) {
                this.type = type;
            }

            public void setCommentlist(String commentlist) {
                this.commentlist = commentlist;
            }

            public int getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public String getUrl() {
                return url;
            }

            public String getListimage() {
                return listimage;
            }

            public String getSmallimage() {
                return smallimage;
            }

            public String getLargeimage() {
                return largeimage;
            }

            public String getPubdate() {
                return pubdate;
            }

            public boolean isComment() {
                return comment;
            }

            public String getCommenturl() {
                return commenturl;
            }

            public String getType() {
                return type;
            }

            public String getCommentlist() {
                return commentlist;
            }
        }
    }
}
