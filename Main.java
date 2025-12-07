import functions.FunctionPoint;
import functions.InappropriateFunctionPointException;
import functions.TabulatedFunction;
import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;


public class Main {
    public static void main(String[] args) {
    TabulatedFunction parabola;

    if (args.length > 0 && args[0].equalsIgnoreCase("linked")) {
            parabola = new LinkedListTabulatedFunction(0.0, 10.0, 11);
            System.out.println("=== Используется LinkedListTabulatedFunction ===");
        } else {
            parabola = new ArrayTabulatedFunction(0.0, 10.0, 11);
            System.out.println("=== Используется ArrayTabulatedFunction ===");
        }

    for (int i = 0; i < parabola.getPointsCount(); i++) {
            double x = parabola.getPointX(i);
            double y = x * x; 
            parabola.setPointY(i, y);
        }
        System.out.println("Табулированная функция y = x^2:");
        System.out.println("Область определения: от " + parabola.getLeftDomainBorder() + 
                         " до " + parabola.getRightDomainBorder());
        System.out.println("Количество точек: " + parabola.getPointsCount());
        
        System.out.println("Точки функции:");
        for (int j = 0; j < parabola.getPointsCount(); j++) {
            System.out.println("  (" + parabola.getPointX(j) + "; " + parabola.getPointY(j) + ")");
        }
        
        System.out.println("\n getPoint()");
        System.out.println("\n Получение точки с индексом 5:");
        FunctionPoint point5 = parabola.getPoint(5);
        System.out.println("   Точка[5] = (" + point5.get_x() + "; " + point5.get_y() + ")");
        
        System.out.println("\n Проверка исключений");
        
        try {
            // 1. getPoint() с неверным индексом 25:
            System.out.println("\n1. Попытка получить точку с индексом 25:");
            parabola.getPoint(25);
            System.out.println("   ОШИБКА: Исключение не было выброшено!");
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
        }
        
        try {
            // 2.setPoint() с нарушением порядка X 
            System.out.println("\n2. Попытка установить точку с нарушением порядка X:");
            FunctionPoint badPoint = new FunctionPoint(1.0, 100.0); // X=1.0 уже существует
            parabola.setPoint(2, badPoint);
            System.out.println("   ОШИБКА: Исключение не было выброшено!");
        } catch (Exception e) {
            System.out.println( e.getClass().getSimpleName() + 
                             " - " + e.getMessage());
        }
        
        try {
            // 3. addPoint() с существующим X 
            System.out.println("\n3. Попытка добавить точку с существующим X (5.0):");
            parabola.addPoint(new FunctionPoint(5.0, 50.0));
            System.out.println("   ОШИБКА: Исключение не было выброшено!");
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName() + 
                             " - " + e.getMessage());
        }
        
        try {
            // 4. setPointX() с нарушением порядка
            System.out.println("\n4. Попытка установить X точки[3] = 1.0 (нарушение порядка):");
            parabola.setPointX(3, 1.0);
            System.out.println("   ОШИБКА: Исключение не было выброшено!");
        } catch (Exception e) {
            System.out.println( e.getClass().getSimpleName() + 
                             " - " + e.getMessage());
        }
        
        try {
            // 5.  deletePoint() когда точек меньше 3
            System.out.println("\n5. Попытка удалить точку при недостаточном количестве:");
            TabulatedFunction smallFunc = new ArrayTabulatedFunction(0.0, 2.0, 2);
            smallFunc.deletePoint(0);
            System.out.println("   ОШИБКА: Исключение не было выброшено!");
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName() + 
                             " - " + e.getMessage());
        }
        
        try {
            // 6. конструктор с некорректными границами
            System.out.println("\n6. Попытка создать функцию с leftX >= rightX:");
            new ArrayTabulatedFunction(10.0, 5.0, 10);
            System.out.println("   ОШИБКА: Исключение не было выброшено!");
        } catch (Exception e) {
            System.out.println( e.getClass().getSimpleName() + 
                             " - " + e.getMessage());
        }

        try {
            // 7 конструктор с одной точкой
            System.out.println("\n7. Конструктор с pointsCount < 2:");
            new ArrayTabulatedFunction(0.0, 10.0, 1);
            System.out.println(" ОШИБКА: Исключение не было выброшено!");
            } catch (IllegalArgumentException e) {
                System.out.println( e.getClass().getSimpleName() + " - " + e.getMessage());
            }

        try {
            //8  setPoint() с неверным индексом
            System.out.println("\n8. setPointY() с индексом 100:");
            parabola.setPointY(100, 50.0);
            System.out.println(" ОШИБКА: Исключение не было выброшено!");
            } catch (Exception e) {
                System.out.println( e.getClass().getSimpleName() + " - " + e.getMessage());
            }

        try {
            // 9 getPointX
            System.out.println("\n9. getPointX() с индексом -5:");
            parabola.getPointX(-5);
            System.out.println("  ОШИБКА: Исключение не было выброшено!");
            } catch (Exception e) {
                System.out.println( e.getClass().getSimpleName() + " - " + e.getMessage());
            }
        try {
            // 10 getPointY
            System.out.println("\n10. getPointX() с индексом 57:");
            parabola.getPointY(57);
            System.out.println("  ОШИБКА: Исключение не было выброшено!");
            } catch (Exception e) {
                System.out.println( e.getClass().getSimpleName() + " - " + e.getMessage());
            }
        

        System.out.println("\n Замена точки с индексом 5:");
        FunctionPoint newPoint = new FunctionPoint(5.0, 30.0);
        try {
            parabola.setPoint(5, newPoint);
        } catch (InappropriateFunctionPointException e) {
            System.out.println("Ошибка: точка " + newPoint + " не может быть добавлена.");
        }
        System.out.println("   После замены: (" + parabola.getPointX(5) + "; " + parabola.getPointY(5) + ")");

        System.out.println("\n setPointX() и setPointY()");
        System.out.println("\n Изменение X точки с индексом 3:");
        System.out.println("   До: (" + parabola.getPointX(3) + "; " + parabola.getPointY(3) + ")");
        try {
            parabola.setPointX(3, 3.5);
            System.out.println("   После: (" + parabola.getPointX(3) + "; " + parabola.getPointY(3) + ")");
        } catch (InappropriateFunctionPointException e) {
            System.out.println("   Ошибка при изменении X: " + e.getMessage());
        }
        System.out.println("\n Изменение Y точки с индексом 3:");
        parabola.setPointY(3, 20.0);
        System.out.println("   После изменения Y: (" + parabola.getPointX(3) + "; " + parabola.getPointY(3) + ")");
        
          System.out.println("\n addPoint()");
        System.out.println("Точки до добавления (первые 5):");
        for (int i = 0; i < Math.min(5, parabola.getPointsCount()); i++) {
            System.out.println("   [" + i + "] = (" + parabola.getPointX(i) + "; " + parabola.getPointY(i) + ")");
        }
        
        System.out.println("\n Добавление новой точки (2.5, 10.0):");
         try {
            parabola.addPoint(new FunctionPoint(2.5, 10.0));
            System.out.println("   Количество точек после добавления: " + parabola.getPointsCount());
            
            System.out.println("\nПоиск добавленной точки:");
            for (int i = 0; i < parabola.getPointsCount(); i++) {
                if (Math.abs(parabola.getPointX(i) - 2.5) < 1e-10) {
                    System.out.println("   Найдена в позиции " + i + ": (" + 
                                     parabola.getPointX(i) + "; " + parabola.getPointY(i) + ")");
                    break;
                }
            }
        } catch (InappropriateFunctionPointException e) {
            System.out.println("   Ошибка добавления точки: " + e.getMessage());
        }
        System.out.println("\n Поиск добавленной точки:");
        for (int i = 0; i < parabola.getPointsCount(); i++) {
            if (Math.abs(parabola.getPointX(i) - 2.5) < 1e-10) {
                System.out.println("   Найдена в позиции " + i + ": (" + 
                                 parabola.getPointX(i) + "; " + parabola.getPointY(i) + ")");
                break;
            }
        }
        
        System.out.println("\n deletePoint()");
        System.out.println("Удаление точки с индексом 2:");
        System.out.println("   Точка[2] до удаления: (" + parabola.getPointX(2) + "; " + parabola.getPointY(2) + ")");
        parabola.deletePoint(2);
        System.out.println("   Количество точек после удаления: " + parabola.getPointsCount());
        System.out.println("   Точка[2] после удаления: (" + parabola.getPointX(2) + "; " + parabola.getPointY(2) + ")");


        System.out.println("\n Проверка порядка точек после всех операций");
        System.out.println("X координаты в порядке возрастания:");
        for (int i = 0; i < parabola.getPointsCount(); i++) {
            System.out.println("   [" + i + "] X = " + parabola.getPointX(i));
        }
        // Вычисляем значение функции в некоторых точках
        System.out.println("\nЗначения функции:");
        System.out.println("f(1.0) = " + parabola.getFunctionValue(1.0));
        System.out.println("f(2.5) = " + parabola.getFunctionValue(2.5));
        System.out.println("f(6.4) = " + parabola.getFunctionValue(6.4));
        System.out.println("f(11.0) = " + parabola.getFunctionValue(11.0)); 
    
}
}

