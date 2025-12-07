package functions;

public class LinkedListTabulatedFunction implements TabulatedFunction{

    private static class FunctionNode{
        public FunctionPoint point;

        public FunctionNode prevEl;
        public FunctionNode nextEl;

        public FunctionNode(FunctionPoint point){  //конструктор с точкой. создает копию точки
            this.point=new FunctionPoint(point);
            this.prevEl=this.nextEl=null;
        }
    }

    private FunctionNode head; // ссылка на голову списка
    private int pointsCount;

    private FunctionNode lastNode;
    private int lastIndex;

    public LinkedListTabulatedFunction(){ // конструктор
        head= new FunctionNode(new FunctionPoint(0, 0));
        head.prevEl=head;
        head.nextEl=head;
        pointsCount=0;
        lastNode=null;
        lastIndex=-1;
    }

    private void initHead(){
        head= new FunctionNode(new FunctionPoint(0, 0));
        head.prevEl=head;
        head.nextEl=head;
        pointsCount=0;
        lastNode=null;
        lastIndex=-1;
    }

    private FunctionNode getNodeByIndex(int index){
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException();
        }

        if (lastNode != null && lastIndex == index){  //если ищем тот же индекс, что и в прошлый раз
            return lastNode;
        }

        if (lastNode != null && lastIndex == index-1){ //если ищем следующий элемент
            lastNode = lastNode.nextEl;
            lastIndex= index;

            return lastNode;
        }

        if (lastNode != null && lastIndex == index+1){ //если ищем предыдущий элемент
            lastNode = lastNode.prevEl;
            lastIndex= index;

            return lastNode;
        }

        FunctionNode current =head.nextEl;
        int currentIndex=0;
        while (currentIndex < index){
            current =current.nextEl;
            currentIndex++;
        }

        lastNode=current;
        lastIndex=index;
        
        return current;

    }

    private FunctionNode addNodeToTail(){
        FunctionNode newNode= new FunctionNode(new FunctionPoint(0, 0)); // создаем новый узел без данных
        newNode.prevEl = head.prevEl; // предыдущий для нового элемента- хвост старого
   // так как циклический узел. То предыдущий для нового, это предыдущий для головы 
        newNode.nextEl= head; // следующий для нового - голова

        head.prevEl.nextEl=newNode; //старый хвост указывает на новый
        head.prevEl=newNode; //предыдущий для головы- новый элемент
    
        pointsCount++;
        lastNode=null;
        lastIndex=-1;

        return newNode;
    }

    private FunctionNode addNodeByIndex(int index){
        if (index < 0 || index > pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException();
        }

        if (index == pointsCount) {
            return addNodeToTail();
        }

        FunctionNode newNode= new FunctionNode(new FunctionPoint(0, 0));
        
        FunctionNode targetNode= getNodeByIndex(index);

        newNode.prevEl=targetNode.prevEl; //предыдущий для нового -предыдущий для старого
        newNode.nextEl=targetNode; //следующий для нового -старый

        targetNode.prevEl.nextEl=newNode; //предыдущий target теперь указывает на новый
        targetNode.prevEl=newNode; //target теперь указывает на новый как на предыдущий
        
        pointsCount++;
        lastNode=null;
        lastIndex=-1;

        return newNode;
    }

    private FunctionNode deleteNodeByIndex(int index){
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException();
        }

        FunctionNode nodeToDelete= getNodeByIndex(index);

        nodeToDelete.prevEl.nextEl=nodeToDelete.nextEl;
        nodeToDelete.nextEl.prevEl=nodeToDelete.prevEl;
        
        nodeToDelete.prevEl=null;
        nodeToDelete.nextEl=null;

        pointsCount--;
        lastNode=null;
        lastIndex=-1;

        return nodeToDelete;
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, int pointsCount){

        if (leftX >= rightX) {
            throw new IllegalArgumentException("Левая граница (" + leftX + 
                ") должна быть меньше правой границы (" + rightX + ")");
        }
        if (pointsCount < 2) {
            throw new IllegalArgumentException("Количество точек (" + pointsCount + 
                ") должно быть не менее 2");
        }

        this.pointsCount = pointsCount;
        initHead();

        double step = (rightX - leftX) / (pointsCount - 1);
        
        for (int i = 0; i < pointsCount; i++) {
            double x = leftX + i * step;
            FunctionNode newNode = addNodeToTail();
            newNode.point = new FunctionPoint(x, 0.0);
        }
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, double[] values){

        if (leftX >= rightX) {
            throw new IllegalArgumentException("Левая граница (" + leftX + 
                ") должна быть меньше правой границы (" + rightX + ")");
        }
        if (values.length < 2) {
            throw new IllegalArgumentException("Количество точек (" + values.length + 
                ") должно быть не менее 2");
        }

        this.pointsCount = values.length;
        initHead(); 

        double step = (rightX - leftX) / (pointsCount - 1);
        
        for (int i = 0; i < pointsCount; i++) {
            double x = leftX + i * step;
            FunctionNode newNode = addNodeToTail();
            newNode.point = new FunctionPoint(x, values[i]);
        }
    }

     public double getLeftDomainBorder(){
        if (pointsCount == 0) return Double.NaN;
        return head.nextEl.point.get_x();
    }

    public double getRightDomainBorder(){
        if (pointsCount == 0) return Double.NaN;
        return head.prevEl.point.get_x();
    }

    public double getFunctionValue(double x){
        if (pointsCount == 0) return Double.NaN; 

        if (x < getLeftDomainBorder() || x > getRightDomainBorder()) {
            return Double.NaN;
        }

        FunctionNode current = (lastNode != null) ? lastNode : head.nextEl;

         while (current != head){
            double currentX = current.point.get_x();

            if (Math.abs(x - currentX) < 1e-10) {
                return current.point.get_y();
            }
        
        if (current.nextEl != head) {
            double nextX = current.nextEl.point.get_x();
            
            if (x > currentX && x < nextX){
                double y1 = current.point.get_y();
                double y2 = current.nextEl.point.get_y();
                return y1 + (y2 - y1) * (x - currentX) / (nextX - currentX);
            }
        }

        current=current.nextEl;
    }
    return Double.NaN;
    }

    public int getPointsCount() {
            return pointsCount;
        }
    
    public FunctionPoint getPoint(int index){
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException();
        }
        FunctionNode node = getNodeByIndex(index);
        return new FunctionPoint(node.point); 
    }

    public void setPoint(int index, FunctionPoint point)
            throws InappropriateFunctionPointException{

        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException ();
        }

        if (point == null) {
            throw new IllegalArgumentException("Точка не может быть null");
        }

        FunctionNode node = getNodeByIndex(index);

        if (index > 0) {
            double leftX = node.prevEl.point.get_x();
            if (point.get_x() <= leftX) {
                throw new InappropriateFunctionPointException("X должен быть больше чем у левой точки");
            }
        }
        if (index < pointsCount - 1) {
            double rightX = node.nextEl.point.get_x();
            if (point.get_x() >= rightX) {
                throw new InappropriateFunctionPointException("X должен быть меньше чем у правой точки");
            }
        }
        node.point = new FunctionPoint(point);
    }

    public double getPointX(int index){
        if (index < 0 || index >= pointsCount) {
         throw new FunctionPointIndexOutOfBoundsException();
    }
        return getNodeByIndex(index).point.get_x();
    }

    public void setPointX(int index, double x)
            throws InappropriateFunctionPointException{
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException();
        }

        FunctionNode node = getNodeByIndex(index);

        if (index > 0 && x <= node.prevEl.point.get_x()) {
            throw new InappropriateFunctionPointException("X должен быть больше чем у левой точки");
        }
        if (index < pointsCount - 1 && x >= node.nextEl.point.get_x()) {
            throw new InappropriateFunctionPointException("X должен быть меньше чем у правой точки");
        }
        node.point.set_x(x);
    }

    public double getPointY(int index){
        if (index < 0 || index >= pointsCount) {
         throw new FunctionPointIndexOutOfBoundsException();
    }
        return getNodeByIndex(index).point.get_y();
    }

    public void setPointY(int index, double y){
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException();
        }

        FunctionNode node = getNodeByIndex(index);

        node.point.set_y(y);
     }

    public void deletePoint(int index){
        
         if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException();
        }

        if(pointsCount<3){
            throw new IllegalStateException(
                "Невозможно удалить точку: в наборе менее 3 точек");
                
        }

        deleteNodeByIndex(index);
    }

    public void addPoint(FunctionPoint point)
        throws InappropriateFunctionPointException    {
            if (point == null) {
                throw new IllegalArgumentException("Точка не может быть null");
        }
    
        FunctionNode current = head.nextEl;
        for (int i = 0; i < pointsCount; i++) {
            if (Math.abs(point.get_x() - current.point.get_x()) < 1e-10) {
                throw new InappropriateFunctionPointException("Точка с таким x уже существует");
            }
            current = current.nextEl; 
        }

        int insertIndex = pointsCount; 
        current = head.nextEl;

        for (int i = 0; i < pointsCount; i++) {
            if (point.get_x() < current.point.get_x()) {
                insertIndex = i;
                break;
            }
            current = current.nextEl;
        }

        FunctionNode newNode = addNodeByIndex(insertIndex);
        newNode.point = new FunctionPoint(point);
     }

}



    
    

