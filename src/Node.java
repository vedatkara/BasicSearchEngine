public class Node<F> {
    private F file;
    private int count;
    private Node<F> next;

    public Node(F file){
        this.file = file;
        count = 1;
        next = null;
    }

    public F getFile() {
        return file;
    }

    public void setFile(F file) {
        this.file = file;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Node<F> getNext() {
        return next;
    }

    public void setNext(Node<F> next) {
        this.next = next;
    }
}
