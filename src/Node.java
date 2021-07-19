/**
 * @date 5 Apr 2021
 * @author damianchng
 */
public class Node<E extends Comparable<E>>{

    // 3 fields
    private E value;
    private Node<E> left;
    private Node<E> right;
    
    // 2 constructors, a one-arg and a three-arg, similar to TreeNode
    public Node(E value){
        this.value = value;
        this.left = null;
        this.right = null;
    }
    
    public Node(E value, Node<E> left, Node<E> right){
        this.value = value;
        this.left = left;
        this.right = right;
    }

    //Getters and Setters
    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public Node<E> getLeft() {
        return left;
    }

    public void setLeft(Node<E> left) {
        this.left = left;
    }

    public Node<E> getRight() {
        return right;
    }

    public void setRight(Node<E> right) {
        this.right = right;
    }
}
