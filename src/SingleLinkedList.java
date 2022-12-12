public class SingleLinkedList<F> {
    Node<F> head;

    public SingleLinkedList(){
        head = null;
    }

    public void add(Node<F> node){
        if (head == null) {
            head = node;
        }
        else{
            Node<F> temp = head;
            boolean found = false;
            while (true){

                if(temp.getFile().equals(node.getFile())){
                    temp.setCount(temp.getCount() + 1);
                    found = true;
                }
                Node<F> temp2 = temp.getNext();
                if(temp2 != null)
                    temp = temp2;
                else
                    break;
            }
            if(!found)
                temp.setNext(node);
        }
    }

    public Node<F> searchFile(String fileName){
        if(head == null)
            return null;
        else{
            Node<F> temp = head;
            while(temp != null){
                if(temp.getFile().equals(fileName))
                    return temp;
                temp = temp.getNext();
            }
        }
        return null;
    }

    public Node<F> findMax(){
        if(head == null)
            return null;
        else{
            Node<F> temp = head;
            int max = 0, count;
            Node<F> maxNode = null;
            while(temp != null){
                count = temp.getCount();
                if(count > max){
                    max = count;
                    maxNode = temp;
                    temp = temp.getNext();
                }
                else
                    temp = temp.getNext();
            }
            return maxNode;
        }

    }

}
