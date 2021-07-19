
import java.util.Queue;
import java.util.Stack;

/**
 * @param <E>
 * @date 5 Apr 2021
 * @author damianchng
 */
public class BST<E extends Comparable<E>> implements BSTinterface<E> {

    // 2 fields
    private Node<E> root;
    private int size;

    // A default constructor
    public BST() {
        root = null;
        size = 0;
    }

    private int height(Node node) { //used to balance tree
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
    }

    private Node rightRotate(Node<E> node) { //used to balance tree
        Node left = node.getLeft();
        Node leftRight = left.getRight();
        left.setRight(node);
        node.setLeft(leftRight);
        if (node == root) {
            root = left;
        }

        return left;
    }

    private Node leftRotate(Node<E> node) { //used to balance tree
        Node right = node.getRight();
        Node rightLeft = right.getLeft();
        right.setLeft(node);
        node.setRight(rightLeft);
        if (node == root) {
            root = right;
        }

        return right;
    }

    @Override
    public boolean contains(E element) {
        return contains(root, element);
    }

    private boolean contains(Node<E> node, E element)
    {
        if (node == null) {
            return false;
        } else if (element.compareTo(node.getValue()) == 0) {
            return true;
        } else if (element.compareTo(node.getValue()) < 0) {
            return contains(node.getLeft(), element);
        } else {
            return contains(node.getRight(), element);
        }
    }

    //lots more methods
    @Override
    public E add(E element) { //add element with avl
        size++;
        if(root == null){
            root = new Node<E>(element);
            return element;
        }
        Stack<Node<E>> ancestors = new Stack<>();
        Node<E> newNode = new Node<E>(element);
        Node<E> child = root;
        Node<E> insert = null;

        while (child != null) {
            insert = child;
            ancestors.push(child);
            if (element.compareTo(child.getValue()) <= 0) {
                child = child.getLeft();
            } else if (element.compareTo(child.getValue()) > 0) {
                child = child.getRight();
            }
        }
        if (insert == null) {
            root = newNode;
        } else if (element.compareTo(insert.getValue()) <= 0) {
            insert.setLeft(newNode);
        } else {
            insert.setRight(newNode);
        }
        
        //normal insert up to here

        while (!ancestors.isEmpty()) {
            Node<E> node = ancestors.pop();
            Node<E> parent = null;
            Node<E> sub = null;
            if (root != node) {
                parent = ancestors.peek();
            }
            int balance = height(node.getLeft()) - height(node.getRight());
            if (balance > 1 || balance < -1) {
                if (node.getLeft() != null) {
                    E left = node.getLeft().getValue();
                    if (balance > 1 && left.compareTo(element) >= 0) { //Left Left
                        sub = rightRotate(node);
                    } else if (balance > 1 && left.compareTo(element) < 0) { //Left Right
                        node.setLeft(leftRotate(node.getLeft()));
                        sub = rightRotate(node);
                    }
                }
                if (node.getRight() != null) {
                    E right = node.getRight().getValue();
                    if (balance < -1 && right.compareTo(element) < 0) {  //Right Right
                        sub = leftRotate(node);
                    } else if (balance < -1 && right.compareTo(element) >= 0) { //Right Left
                        node.setRight(rightRotate(node.getRight()));
                        sub = leftRotate(node);
                    }
                }
                if (parent == null) {
                    root = sub;
                } else if (parent.getLeft() == node && sub != null) {
                    parent.setLeft(sub);
                } else if (sub != null) {
                    parent.setRight(sub);
                }
            }
        }
        //rotations to balance tree up to here
        return element;
    }

    @Override
    public boolean isEmpty() { //check if is empty
        return root == null;
    }

    @Override
    public E delete(E element) { //delete element with avl
        if(root == null){
            return null;
        }
        Stack<Node<E>> ancestors = new Stack<>();
        Node<E> delete = root;
        Node<E> parent = root;

        boolean isLeftChild = false;

        while (!(element.compareTo(delete.getValue()) == 0)) {
            parent = delete;
            ancestors.push(delete);
            if (element.compareTo(delete.getValue()) <= 0) {
                isLeftChild = true;
                delete = delete.getLeft();
            } else {
                isLeftChild = false;
                delete = delete.getRight();
            }

            if (delete == null) {
                return null;
            }
        }
        size--;
        if (delete.getLeft() == null && delete.getRight() == null) { //leaf
            if (delete == root) {
                root = null;
            } else if (isLeftChild) {
                parent.setLeft(null);
            } else {
                parent.setRight(null);
            }
        } 
        else if (delete.getRight() == null){ // has left child 
            if (delete == root) {
                root = delete.getLeft();
            } else if (isLeftChild) {
                parent.setLeft(delete.getLeft());
            } else {
                parent.setRight(delete.getLeft());
            }
        }
        else if (delete.getLeft() == null) { // has right child
            if (delete == root) {
                root = delete.getRight();
            } else if (isLeftChild) {
                parent.setLeft(delete.getRight());
            } else {
                parent.setRight(delete.getRight());
            }
        } 
        else { //two children
            Node successor = getSuccessor(delete);
            if (delete == root) {
                root = successor;
            } else if (isLeftChild) {
                parent.setLeft(successor);
            } else {
                parent.setRight(successor);
            }
            successor.setLeft(delete.getLeft());
        }
        
        //standard delete up to here

        while (!ancestors.isEmpty()) {
            Node<E> node = ancestors.pop();
            parent = null;
            Node<E> sub = null;
            if (root != node) {
                parent = ancestors.peek();
            }
            int balance = height(node.getLeft()) - height(node.getRight());
            if (balance > 1 || balance < -1) {

                if (node.getLeft() != null) {
                    E left = node.getLeft().getValue();
                    if (balance > 1 && left.compareTo(element) >= 0) {  //Left Left
                        sub = rightRotate(node);
                    } else if (balance > 1 && left.compareTo(element) < 0) {  //Left Right
                        node.setLeft(leftRotate(node.getLeft()));
                        sub = rightRotate(node);
                    }
                }
                if (node.getRight() != null) {
                    E right = node.getRight().getValue();
                    if (balance < -1 && right.compareTo(element) < 0) { //Right Right
                        sub = leftRotate(node);
                    } else if (balance < -1 && right.compareTo(element) >= 0) { //Right Left
                        node.setRight(rightRotate(node.getRight()));
                        sub = leftRotate(node);
                    }
                }
                if (parent == null) {
                    root = sub;
                } else if (parent.getLeft() == node) {
                    parent.setLeft(sub);
                } else {
                    parent.setRight(sub);
                }
            }
        }
        
        //rotations to balance tree up to here

        return element;
    }

    private Node getSuccessor(Node node) { //Helper method for delete - gets min node on right
        Node minParent = node;
        Node min = node.getRight();
        while (min.getLeft() != null) {
            minParent = min;
            min = min.getLeft();
        }
        if (minParent != node) {
            minParent.setLeft(min.getRight());
            min.setRight(node.getRight());
        }
        return min;
    }

    @Override
    public int size() { //size of bst
        return size;
    }

    @Override
    public boolean edit(E oldElement, E newElement) { //edit old value - uses delete and add
        if (!contains(oldElement)) {
            return false;
        } else {
            delete(oldElement);
            add(newElement);
            return true;
        }
    }

    @Override
    public String toString() { //Tree in proper format
        return toString(root, 0);
    }

    private String toString(Node<E> t, int level) { //Helper method - Returns tree in proper format
        String toRet = "";
        if (t == null) {
            return "";
        }
        toRet += toString(t.getRight(), level + 1);
        for (int k = 0; k < level; k++) {
            toRet += "\t";
        }
        toRet += t.getValue() + "\n";
        toRet += toString(t.getLeft(), level + 1);
        return toRet;
    }

    public E searchByLevel(E user) { //Searching tree based on level - uses bst mechanism to traverse in O(log n( time
        int minDiff = 11;
        E minDiffValue = null;
        return searchByLevel(root, user, minDiff, minDiffValue);
    }

    private E searchByLevel(Node<E> node, E user, int minDiff, E minDiffValue) { //helper method to search tree on level - O(log n time)
        if (node == null) {
            return minDiffValue;
        }
        int currentDiff = ((TennisPlayer) user).compareLevel((TennisPlayer) node.getValue());

        if (currentDiff == 0) {
            return node.getValue();
        }

        if (Math.abs(currentDiff) < minDiff) {
            minDiff = Math.abs(currentDiff);
            minDiffValue = node.getValue();
        }

        if (currentDiff < 0) {
            return searchByLevel(node.getLeft(), user, minDiff, minDiffValue);
        } else {
            return searchByLevel(node.getRight(), user, minDiff, minDiffValue);
        }
    }

    public E searchByLocation(E user) { //search tree by location - preorder traversal O(N)
        Stack<Node<E>> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node<E> node = stack.pop();
            if (node != null) {
                if (((TennisPlayer) user).compareLocation((TennisPlayer) node.getValue())) {
                    return node.getValue();
                } else {
                    stack.push(node.getRight());
                    stack.push(node.getLeft());
                }
            }
        }
        return null;
    }

    public E searchBySex(E user) { //search tree by sex - preorder traversal O(N)
        Stack<Node<E>> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node<E> node = stack.pop();
            if (node != null) {
                if (((TennisPlayer) user).compareSex((TennisPlayer) node.getValue())) {
                    return node.getValue();
                } else {
                    stack.push(node.getRight());
                    stack.push(node.getLeft());
                }
            }
        }
        return null;
    }

    public E searchByAge(E user) { //search tree by age - preorder traversal O(N)
        Stack<Node<E>> stack = new Stack<>();
        stack.push(root);
        int minDiff = 11;
        E minDiffValue = null;

        while (!stack.isEmpty()) {
            Node<E> node = stack.pop();
            if (node != null) {
                int currentDiff = ((TennisPlayer) user).compareAge((TennisPlayer) node.getValue());

                if(currentDiff == 0){
                    return node.getValue();
                }
                
                if (Math.abs(currentDiff) < minDiff) {
                    minDiff = Math.abs(currentDiff);
                    minDiffValue = node.getValue();
                }
                stack.push(node.getRight());
                stack.push(node.getLeft());
            }
        }
        return minDiffValue;
    }
    
    public E findBest(){ //max node - on the right O(log n)
        if(root == null){
            return null;
        }
        Node<E> current = root;
        while(current.getRight() != null){
            current = current.getRight();
        }
        return current.getValue();
    }
    
    public E findWorst(){ //min node - on the left O(log n)
        if(root == null){
            return null;
        }
        Node<E> current = root;
        while(current.getLeft() != null){
            current = current.getLeft();
        }
        return current.getValue();
    }
    
    public double findAverage(){ //average level - preorder traverse O(N)
        if(root == null){
            return 0;
        }
        Stack<Node<E>> stack = new Stack<>();
        stack.push(root);
        int total = 0;

        while (!stack.isEmpty()) {
            Node<E> node = stack.pop();
            if (node != null) {
                total += ((TennisPlayer)node.getValue()).getLevel();
                stack.push(node.getRight());
                stack.push(node.getLeft());
            }
        }
        return ((double)total)/size;
    }
    
    public String[] getAllNodes(){ //all nodes in order - inorder traverse O(N)
        if(root == null){
            return null;
        }
        String[] arr = new String[size];
        Node<E> current = root;
        int i = 0;
        Stack<Node<E>> stack = new Stack<>();
        while (!stack.isEmpty() || current != null) {
            while(current != null){
                stack.push(current);
                current = current.getLeft();
            }
            current = stack.pop();
            arr[i] = current.getValue().toString();
            current = current.getRight();
            i++;
        }
        return arr;
    }
}
