package com.jjshome.mobile.datastatistics.entity;

import java.util.List;

public class AppMarqueeMetricsData {

    /**
     * "msg": "868ms",
     * "data": {
     * "dimName": null,
     * "indexCount": 1,
     * "page": {
     * "list": null,
     * "totalNum": 0,
     * "currentPage": 1,
     * "pageSize": 20,
     * "totalPages": 0,
     * "previousPage": 1,
     * "nextPage": 2,
     * "startPage": 0,
     * "endPage": 20
     * },
     * "list": [   // 折线图数据
     * {
     * "DAY": {
     * "type": 2,  // 横坐标
     * "value": "2020/05/26",
     * "order": 0
     * },
     * "PV": {
     * "type": 1,  // 纵坐标
     * "value": 466,
     * "order": 1
     * }
     * }
     * ],
     * "type": 2,
     * "dimData": null,
     * "dimCount": 1
     * },
     * "success": true, // 是否成功
     * "status": true
     */

    private String msg;
    private boolean success;// 是否成功
    private String status;
    private MarqueeMetricsData data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MarqueeMetricsData getData() {
        return data;
    }

    public void setData(MarqueeMetricsData data) {
        this.data = data;
    }

    public class MarqueeMetricsData {
        private String dimName;
        private String indexCount;
        private Page page;
        private List<ChartsData> list;
        private String type;
        private String dimData;
        private String dimCount;

        public String getDimName() {
            return dimName;
        }

        public void setDimName(String dimName) {
            this.dimName = dimName;
        }

        public String getIndexCount() {
            return indexCount;
        }

        public void setIndexCount(String indexCount) {
            this.indexCount = indexCount;
        }

        public Page getPage() {
            return page;
        }

        public void setPage(Page page) {
            this.page = page;
        }

        public List<ChartsData> getList() {
            return list;
        }

        public void setList(List<ChartsData> list) {
            this.list = list;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDimData() {
            return dimData;
        }

        public void setDimData(String dimData) {
            this.dimData = dimData;
        }

        public String getDimCount() {
            return dimCount;
        }

        public void setDimCount(String dimCount) {
            this.dimCount = dimCount;
        }

        public class Page {
            private String list;
            private String totalNum;
            private String currentPage;
            private String pageSize;
            private String totalPages;
            private String previousPage;
            private String nextPage;
            private String startPage;
            private String endPage;

            public String getList() {
                return list;
            }

            public void setList(String list) {
                this.list = list;
            }

            public String getTotalNum() {
                return totalNum;
            }

            public void setTotalNum(String totalNum) {
                this.totalNum = totalNum;
            }

            public String getCurrentPage() {
                return currentPage;
            }

            public void setCurrentPage(String currentPage) {
                this.currentPage = currentPage;
            }

            public String getPageSize() {
                return pageSize;
            }

            public void setPageSize(String pageSize) {
                this.pageSize = pageSize;
            }

            public String getTotalPages() {
                return totalPages;
            }

            public void setTotalPages(String totalPages) {
                this.totalPages = totalPages;
            }

            public String getPreviousPage() {
                return previousPage;
            }

            public void setPreviousPage(String previousPage) {
                this.previousPage = previousPage;
            }

            public String getNextPage() {
                return nextPage;
            }

            public void setNextPage(String nextPage) {
                this.nextPage = nextPage;
            }

            public String getStartPage() {
                return startPage;
            }

            public void setStartPage(String startPage) {
                this.startPage = startPage;
            }

            public String getEndPage() {
                return endPage;
            }

            public void setEndPage(String endPage) {
                this.endPage = endPage;
            }
        }

        public class ChartsData {

            ChartsValueData DAY;
            ChartsValueData PV;

            public ChartsValueData getDAY() {
                return DAY;
            }

            public void setDAY(ChartsValueData DAY) {
                this.DAY = DAY;
            }

            public ChartsValueData getPV() {
                return PV;
            }

            public void setPV(ChartsValueData PV) {
                this.PV = PV;
            }

            public class ChartsValueData {
                private String type;  // 横坐标
                private String value;//2020/05/26",
                private String order;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public String getOrder() {
                    return order;
                }

                public void setOrder(String order) {
                    this.order = order;
                }
            }

        }

    }

}
