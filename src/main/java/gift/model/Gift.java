package gift.model;

import candy.model.Candy;
import gift.interfaces.GiftInterface;

import java.util.ArrayList;

public class Gift implements GiftInterface {
    private static int _idCounter = 0;
    private final int _id;
    private String _name;
    private ArrayList<Candy> _candies;
    private int _totalCost;


    public Gift(String name,ArrayList<Candy> candies) {
        _id = ++_idCounter;
        _name = name;
        _candies = new ArrayList<Candy>(candies);
        for (Candy candy : candies) {
            _totalCost += candy.getPrice();
        }
    }
    public Gift(String name){
        _id = ++_idCounter;
        _name = name;
        _candies = new ArrayList<>();
    }

    public Gift(int id, String name) {
        if (id < _idCounter) {
            throw new IllegalArgumentException("ID must be greater than or equal to " + _idCounter);
        }
        _id = id;
        _name = name;
    }
    public Gift (int id, String name, ArrayList<Candy> candies) {
        if (id < _idCounter) {
            throw new IllegalArgumentException("ID must be greater than or equal to " + _idCounter);
        }
        _id = id;
        _name = name;
        _candies = candies;
    }

    public void printGiftInfo(){
        System.out.println("//////////////////////////");
        System.out.println("Id:" + _id);
        System.out.println("Name:" + _name);
        System.out.println("TotalCost:" + _totalCost);
        System.out.println("Candies contains:" + _candies.size());
        System.out.println("//////////////////////////");
    }

    public Candy findCandyById(int id){
        for (Candy candy : _candies) {
            if(candy.getId() == id){
                return candy;
            }
        }
        throw new IllegalArgumentException("Candy not found");
    }

    public void addCandy(Candy candy){
        _candies.add(candy);
        _totalCost += candy.getPrice();
    }

    public void deleteCandy(int id){
        if (id <= 0){
            throw new IllegalArgumentException("Id must be greater than 0");
        }
        Candy candy = findCandyById(id);
        _candies.remove(candy);
        _totalCost -= candy.getPrice();
    }

    public void setCandies(ArrayList<Candy> candies){
        if (candies.isEmpty()){
            throw new IllegalArgumentException("candies size must be not empty");
        }
        for (Candy candy : candies) {
            _totalCost += candy.getPrice();
        }
        _candies = candies;
    }
    public void setName(String name){
        if (name == null){
            throw new IllegalArgumentException("name must not be null");
        }
        _name = name;
    }

    public int getId(){return _id;}
    public String getName(){return _name;}
    public int getTotalCost() {return _totalCost;}
    public ArrayList<Candy> get_candies() {return new ArrayList<>(_candies);}


    private Candy recursiveBinarySearchBySugarPer100(int sugar_min,int sugar_max,int start,int end){
        if (start > end) {
            throw new IllegalArgumentException("Failed to find candy in the specified sugar range.");
        }

        int middle = (start + end) / 2;
        int sugar = _candies.get(middle).getSugarPercentagePer100g();

        if (sugar >= sugar_min && sugar <= sugar_max) {
            return _candies.get(middle);
        }

        if (sugar < sugar_min) {
            return recursiveBinarySearchBySugarPer100(sugar_min, sugar_max, middle + 1, end);
        } else {
            return recursiveBinarySearchBySugarPer100(sugar_min, sugar_max, start, middle - 1);
        }
    }

    public ArrayList<Candy> sort(candy.interfaces.CandySortStrategyInterface strategy){
        ArrayList<Candy> candies = new ArrayList<>(_candies);
        strategy.sort(candies);
        return candies;
    }
}
