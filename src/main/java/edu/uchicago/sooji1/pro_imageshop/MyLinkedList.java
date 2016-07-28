package edu.uchicago.sooji1.pro_imageshop;


/**
 * Created by pikashoes on 7/27/16.
 */
public class MyLinkedList
{
    private Node first;
    private Node current;
    private Node last;
    private int count;

    public MyLinkedList()
    {
        this.first = null;
        this.last = null;
        this.count = 0;
        this.current = null;
    }

    private class Node
    {
        Node next;
        Node previous;
        Object data;
    }

    public boolean hasNext()
    {
        return (current != null && current.next != null);
    }

    public Object next()
    {
        if (!this.hasNext())
        {
            throw new IllegalStateException("There is no next object");
        }

        return current.next.data;
    }

    public void moveToNext()
    {
        if (!this.hasNext())
        {
            throw new IllegalStateException("There is no next object");
        }

        current = current.next;
    }

    public boolean hasPrevious()
    {
        return (current != null && current.previous != null);
    }

    public Object previous()
    {
        if (!this.hasPrevious())
        {
            throw new IllegalStateException("There is no previous object");
        }

        return current.previous.data;
    }

    public void moveToPrevious()
    {
        if (!this.hasPrevious())
        {
            throw new IllegalStateException("There is no previous object");
        }

        current = current.previous;
    }

    public void set(Object object)
    {
        if (this.current == null)
        {
            throw new IllegalStateException("No node found. Failed to set.");
        }
        current.data = object;
    }

    public int size()
    {
        return count;
    }

    public Object returnCurrent()
    {
        return current.data;
    }

    public void add(Object object)
    {
        Node newNode = new Node();
        newNode.data = object;

        if (first == null)
        {
            first = newNode;
            last = newNode;
            newNode.next = null;
        }
        else if (first != null)
        {
            if (current == null)
            {
                newNode.previous = null;
                newNode.next = first;
                first.previous = newNode;
                first = newNode;
            }
            else if (current == last)
            {
                newNode.previous = current;
                newNode.next = null;
                current.next = newNode;
                last = newNode;
            }
            else
            {
                newNode.previous = last;
                newNode.next = null;
            }
        }
        current = newNode;
        count++;
    }

    public void insertAndDeleteNext(Object object)
    {
        Node newNode = new Node();
        newNode.data = object;

        if (hasNext())
        {
            newNode.previous = current;
            newNode.next = current.next;
            current.next.previous = null;
            current.next = newNode;
            newNode.next = null;
        } else
        {
            add(object);
        }

    }

    public void remove()
    {
        if (current != null)
        {
            if (current == first && current == last)
            {
                first = null;
                last = null;
            }
            else if (current == first)
            {
                current.next.previous = null;
                first = current.next;
            }
            else if (current == last)
            {
                current.previous.next = null;
                last = current.previous;
            }
            else
            {
                current.previous.next = current.next;
                current.next.previous = current.previous;
            }

            // This is if the last node is deleted, then we don't want image to be null
            if (current.next == null)
            {
                current = last;
            } else
            {
                current = current.next;
            }
            count--;
        }
    }

}
