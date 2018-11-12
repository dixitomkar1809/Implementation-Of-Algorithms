/** LP3
 * GROUP LP 17 - MEMBERS:    OMKAR DIXIT      netID: ond170030
 *  *                        KARAN KANANI     netID: kyk170030
 *  *                        TEJAS RAVI RAO   netID: txr171830
 *  *                        SHAKTI SINGH     netID: sxs178130
 */


package ond170030.LP3;

// If you want to create additional classes, place them in this file as subclasses of MDS

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;


public class MDS {
    // Add fields of MDS here


    private Map<Long, Item> idMap;
    private Map<Long, TreeSet<Item>> descMap;

    // Constructors
    public MDS() {

        //Use of TreeMap for id.
        idMap = new TreeMap<>();

        //Use of HashMap for description
        descMap = new HashMap<>();
    }


    /* Public methods of MDS. Do not change their signatures.
       ________
       a. Insert(id,price,list): insert a new item whose description is given
       in the list.  If an entry with the same id already exists, then its
       description and price are replaced by the new values, unless list
       is null or empty, in which case, just the price is updated.
       Returns 1 if the item is new, and 0 otherwise.
    */
    public int insert(long id, Money price, java.util.List<Long> list) {
        Item oldItem = idMap.get(id);
        Item item;
        if(list.size()==0)
            item = new Item(id, price, oldItem.getDescription());
        else
            item = new Item(id, price, list);

        // if item is not there in inventory

        if (oldItem == null) {
            idMap.put(id, item);
            updateDescriptionMap(item);

            return 1;
        }
        // if item is already there in inventory
        else {


            idMap.put(id,item);


            for (Long oldDesc : oldItem.getDescription()) {
                Set itemSetDescOld = descMap.get(oldDesc);
                itemSetDescOld.remove(oldItem);
            }


            updateDescriptionMap(item);


            return 0;
        }

    }


    //Helper method to update the Description Map after an insert operation.
    private void updateDescriptionMap(Item item)
    {
        for (Long desc : item.getDescription()) {
            Set descSet = this.descMap.get(desc);
            if (descSet == null) {
                descSet = new TreeSet();
                descSet.add(item);
                this.descMap.put(desc, (TreeSet<Item>) descSet);
            } else {
                descSet.add(item);
            }

        }
    }



    // b. Find(id): return price of item with given id (or 0, if not found).
    public Money find(long id) {

        Item item = idMap.get(id);
        if (item != null)
            return item.getPrice();
        return new Money();
    }



    /*
       c. Delete(id): delete item from storage.  Returns the sum of the
       long ints that are in the description of the item deleted,
       or 0, if such an id did not exist.
    */
    public long delete(long id) {
        long sum = 0;
        Item item = idMap.get(id);
        if(item != null) {

            idMap.remove(id);

            // sum of all long ints in the description of item deleted
            for (Long desc : item.getDescription()) {
                sum += desc;
                Set set = descMap.get(desc);
                set.remove(item);
            }
        }

        return sum;
    }



    /*
       d. FindMinPrice(n): given a long int, find items whose description
       contains that number (exact match with one of the long ints in the
       item's description), and return lowest price of those items.
       Return 0 if there is no such item.
    */
    public Money findMinPrice(long n) {
        Set<Item> items = descMap.get(n);

        // If no items are present with such description, return 0
        if(items==null||items.size()==0){
            return new Money();
        }
        //Else Return the lowest price of those items
        else {

            Iterator<Item> itr=items.iterator();
            Money min = itr.next().getPrice();
            while(itr.hasNext()){
                Item c=itr.next();
                if(min.compareTo(c.getPrice())>0)
                    min = c.getPrice();

            }
            return min;
        }

    }

    /*
       e. FindMaxPrice(n): given a long int, find items whose description
       contains that number, and return highest price of those items.
       Return 0 if there is no such item.
    */
    public Money findMaxPrice(long n) {
        Set<Item> items = descMap.get(n);

        //If no items are present with such description, return 0
        if(items==null||items.size()==0){
            return new Money();
        }
        //Else return the highest price of those items
        else
        {

            Iterator<Item> itr=items.iterator();
            Money max = itr.next().getPrice();
            while(itr.hasNext()){
                Item c=itr.next();
                if(max.compareTo(c.getPrice())<0)
                    max = c.getPrice();

            }
            return max;
        }
    }

    /*
       f. FindPriceRange(n,low,high): given a long int n, find the number
       of items whose description contains n, and in addition,
       their prices fall within the given range, [low, high].
    */
    public int findPriceRange(long n, Money low, Money high) {
        int count=0;

        //handle case where high is less than low
        if(high.compareTo(low) == -1){
            return 0;
        }

        //handle case where no items are present with description n
        TreeSet<Item> items = descMap.get(n);
        if(items == null||items.size()==0){
            return count;
        }


        //Return the items that match description and price fall within range
        for(Item item : items){
            Money price = item.getPrice();
            if(price.compareTo(high)==0 || price.compareTo(low)==0){
                count++;
            }
            else if(price.compareTo(high)==-1 && price.compareTo(low)==1){
                count++;
            }
        }
        return count;
    }


    /*
       g. PriceHike(l,h,r): increase the price of every product, whose id is
       in the range [l,h] by r%.  Discard any fractional pennies in the new
       prices of items.  Returns the sum of the net increases of the prices.
    */

    public Money priceHike(long l, long h, double rate) {
        if(l<=h) {
            long preSum = 0;
            long postSum =0;
            long netIncrease = 0;
            NavigableMap<Long, Item> subsetIdMap = ((TreeMap) idMap).subMap(l, true, h, true);
            for (Item item : subsetIdMap.values()) {

                //Convert price to cents
                preSum = item.getPrice().cents() + item.getPrice().dollars() * 100;

                //Calculate the new price in cents
                postSum = preSum + (long) Math.floor(rate * preSum)/100;

                //convert from cents to Money Format
                Money newPrice = toMoney(postSum);


                item.setPrice(newPrice);

                //Calculate sum of the net increases of the prices
                netIncrease+=(postSum-preSum);
            }

            return toMoney(netIncrease);
        }
        return new Money(); // return 0 if (l>h)
    }



    //Helper method to convert price in cents to Money format: d dollars and c cents
    private Money toMoney(long cents){
        return new Money(cents/100, (int) cents % 100);
    }




    /*
      h. RemoveNames(id, list): Remove elements of list from the description of id.
      It is possible that some of the items in the list are not in the
      id's description.  Return the sum of the numbers that are actually
      deleted from the description of id.  Return 0 if there is no such id.
    */
    public long removeNames(long id, java.util.List<Long> list) {

        long sum = 0;

        Item item = idMap.get(id);

        //handle case where no items exist with such id.
        if(item == null)
            return sum;


        Set<Long> listSet = new HashSet<>();
        List<Long> itemDesc = item.getDescription();

        for(Long desc : list)
        {
            listSet.add(desc);
            Set descSet = descMap.get(desc);
            if(descSet!=null)
                descSet.remove(item);
        }

        Iterator<Long> itr = itemDesc.listIterator();
        while (itr.hasNext()) {
            Long desc = itr.next();
            if (listSet.contains(desc)) {
                sum += desc;
                itr.remove();
            }
        }

        return sum;
    }

    // Do not modify the Money class in a way that breaks LP3Driver.java
    public static class Money implements Comparable<Money> {
        long d;
        int c;

        public Money() {
            d = 0;
            c = 0;
        }

        public Money(long d, int c) {
            this.d = d;
            this.c = c;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Money)) return false;
            Money money = (Money) o;
            return d == money.d &&
                    c == money.c;
        }

        @Override
        public int hashCode() {

            return Objects.hash(d, c);
        }

        public Money(String s) {
            String[] part = s.split("\\.");
            int len = part.length;
            if (len < 1) {
                d = 0;
                c = 0;
            } else if (part.length == 1) {
                d = Long.parseLong(s);
                c = 0;
            } else {
                d = Long.parseLong(part[0]);
                c = Integer.parseInt(part[1]);
            }
        }

        public void setDollars(long d) {
            this.d = d;
        }


        public void setCents(int c) {
            this.c = c;
        }

        public long dollars() {
            return d;
        }

        public int cents() {
            return c;
        }


        public int compareTo(Money other) { //
            if (this.d > other.d)
                return 1;
            else if (this.d < other.d)
                return -1;
            else {
                if (this.c > other.c)
                    return 1;
                else if (this.c < other.c)
                    return -1;
                else
                    return 0;
            }
        }

        public String toString() {
            return d + "." + c;

        }
    }

    //Item class - to hold and modify information about an item
    public static class Item implements Comparable<Item>{
        private MDS.Money price;
        private long id;
        private List<Long> description;

        //Item Constructor
        public Item(long id, MDS.Money price, List<Long> description) {
            this.price = price;
            this.id = id;
            this.description = new LinkedList<>();
            for (Long desc : description) {
                this.description.add(desc);
            }

        }

        public MDS.Money getPrice() {
            return price;
        }

        @Override
        public int compareTo(Item item) {
            return ((Long)this.id).compareTo(item.getId());
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof Item)) return false;
            Item e = (Item) obj;
            if (e.getId() == this.getId())
                return true;
            return false;
        }


        @Override
        public int hashCode() {

            return Objects.hash(getPrice(), getId(), getDescription());
        }

        public void setPrice(MDS.Money price) {
            this.price = price;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public List<Long> getDescription() {
            return description;
        }

        public void setDescription(List<Long> description) {
            this.description = description;
        }


    }


}