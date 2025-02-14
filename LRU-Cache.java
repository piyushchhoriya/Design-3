// ## Problem 2: LRU Cache(https://leetcode.com/problems/lru-cache/)

// Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.

// Implement the LRUCache class:

// LRUCache(int capacity) Initialize the LRU cache with positive size capacity.
// int get(int key) Return the value of the key if the key exists, otherwise return -1.
// void put(int key, int value) Update the value of the key if the key exists. Otherwise, add the key-value pair to the cache. If the number of keys exceeds the capacity from this operation, evict the least recently used key.
// The functions get and put must each run in O(1) average time complexity.

//Approach : I have used hashmap to store the key as key and Node as a value
// To keep track of which key was least recently used I am using an Doubly linked list

//Time Complexity : O(1)
//Space Complexity : O(capacity)
class LRUCache {
    
    class Node{
        int key, value;
        Node prev, next;
        public Node(int key, int value){
            this.key=key;
            this.value=value;
        }
    }
    int capacity;
    Node start,end;
    HashMap<Integer,Node> map=new HashMap<>();
    public LRUCache(int capacity) {
        this.capacity=capacity;
        start = new Node(-1,-1);
        end = new Node(-1,-1);
        start.next=end;
        end.prev=start;
    }
    private void insertfirst(Node node){
        node.next=start.next;
        node.next.prev=node;
        node.prev=start;
        start.next=node;
    }

    private void remove(Node node){
        node.prev.next=node.next;
        node.next.prev=node.prev;
    }
    public int get(int key) {
        if(map.containsKey(key)){
           Node node = map.get(key); 
           remove(node);
           insertfirst(node);
           return node.value;
        }
        return -1;
    }
    
    public void put(int key, int value) {
        if(map.containsKey(key)){
            Node node = map.get(key);
            remove(node);
            node.value=value;
            insertfirst(node);
            map.put(key,node);
        }
        else{
            Node node=new Node(key,value);
            if(map.size()<capacity){
                insertfirst(node);
                map.put(key,node);
                return;
            }
            else{
                Node node1= end.prev;
                remove(node1);
                map.remove(node1.key);
                insertfirst(node);
                map.put(key,node);
            }
        }

    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */