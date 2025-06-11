package application.project.domain.DTO;

public class PageMetadata {
    private int currentPage;
    private int elementPerPage;
    private int totalPage;

    public int getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getElementPerPage() {
        return elementPerPage;
    }
    public void setElementPerPage(int elementPerPage) {
        this.elementPerPage = elementPerPage;
    }
    public int getTotalPage() {
        return totalPage;
    }
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    

   
}
