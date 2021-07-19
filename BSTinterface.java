/**
 *
 * @author damianchng
 */
public interface BSTinterface<E>
{
    public E add( E element ); //returns the object
    public boolean contains( E element );
    public boolean isEmpty();
    public E delete( E element ); //returns the object, not a Node&lt;E&gt;.
    public int size();
    public String toString();
    public boolean edit(E oldElement, E newElement);
}
