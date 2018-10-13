package jp.ac.kyoto_su.ise.tamadalab.bydi;

class Table {
    private int[][] tables;

    Table(int x, int y) {
        this.tables = new int[y][x];
    }

    public int getXSize() {
        return tables[0].length;
    }

    public int getYSize() {
        return tables.length;
    }

    public int get(int x, int y) {
        if(x > tables[0].length || y > tables.length){
            throw new ArrayIndexOutOfBoundsException();
        }
        return tables[y][x];
    }

    public void set(int value, int x, int y) {
        if(x > tables[0].length || y > tables.length){
            throw new ArrayIndexOutOfBoundsException();
        }
        this.tables[y][x] = value;
    }

    public void print(){
        for(Integer j = 0; j < getYSize(); j++){
            for(Integer i = 0; i < getXSize(); i++){
                System.out.printf("%2s ", get(i, j));
            }
            System.out.println();
        }
    }

    public static void main(String[] args){
        Table table = new Table(5, 4);
        for(int i = 0; i < table.getXSize(); i++){
            for(int j = 0; j < table.getYSize(); j++){
                table.set(i + j, i, j);
            }
        }
        table.print();
    }
}
