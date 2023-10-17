import java.io.*;
import java.util.*;

class Employee implements Comparable<Employee> {
    private String jobType;
    private int age;
    private String name;

    public Employee(String name, String jobType, int age) {
        this.name = name;
        this.jobType = jobType;
        this.age = age;
    }

    @Override
    public int compareTo(Employee other) {
        if (!jobType.equals(other.jobType)) {
            return jobType.compareTo(other.jobType);
        }
        if (age != other.age) {
            return Integer.compare(age, other.age);
        }
        return name.compareTo(other.name);
    }

    public String toString() {
        return jobType + ", " + name + ", " + age;
    }
}

class CircularLinkedList implements Iterable<Employee> {
    private Node head;
    private int size;

    private class Node {
        Employee data;
        Node next;

        Node(Employee data) {
            this.data = data;
        }
    }

    public CircularLinkedList() {
        head = null;
        size = 0;
    }

    public void insert(Employee emp) {
        Node newNode = new Node(emp);
        if (head == null) {
            head = newNode;
            head.next = head;
        } else {
            Node current = head;
            while (current.next != head) {
                current = current.next;
            }
            current.next = newNode;
            newNode.next = head;
        }
        size++;
    }

    public void sort() {
        if (head == null) {
            return;
        }

        Node current = head;
        do {
            Node nextNode = current.next;
            do {
                if (nextNode.data.compareTo(current.data) < 0) {
                    Employee temp = nextNode.data;
                    nextNode.data = current.data;
                    current.data = temp;
                }
                nextNode = nextNode.next;
            } while (nextNode != head);

            current = current.next;
        } while (current.next != head);
    }

    @Override
    public Iterator<Employee> iterator() {
        return new EmployeeIterator();
    }

    private class EmployeeIterator implements Iterator<Employee> {
        private Node current = head;
        private int count = 0;

        public boolean hasNext() {
            return count < size;
        }

        public Employee next() {
            if (count == size) {
                throw new NoSuchElementException();
            }
            Employee data = current.data;
            current = current.next;
            count++;
            return data;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

public class container {
    public static void main(String[] args) {
        CircularLinkedList employeeList = new CircularLinkedList();

        try {
            // Read from standard input (System.in)
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    String name = parts[0];
                    String jobType = parts[1];
                    int age = Integer.parseInt(parts[2]);
                    Employee employee = new Employee(name, jobType, age);
                    employeeList.insert(employee);
                }
            }

            // Sort the list
            employeeList.sort();

            // Write to a file named "output.txt" in the format "job title, name, age"
            BufferedWriter writer = new BufferedWriter(new FileWriter("output2.txt"));
            for (Employee emp : employeeList) {
                writer.write(emp.toString());
                writer.newLine();
            }
            writer.close();

            System.out.println("Sorted employee data has been written to output.txt.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
