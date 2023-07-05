import java.security.InvalidParameterException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * nodemap
 */
public class NodeMap {

    Integer width;
    Integer height;
    NodeCell[][] body;
    boolean wallsAreSet;
    boolean pathIsSet;
    Integer const_wall = -2;
    Integer const_start = 1;
    Integer const_blank = 0;
    Integer const_end = -1;
    NodeCell start;
    NodeCell finish;

    public static void main(String[] args) {
        NodeMap test = new NodeMap(10, 10);
        boolean result = test.calculatePath();
        if (result) {
            test.Show();
        } else {
            System.out.println("Finish unreachable");
        }
        System.out.println();

    }

    private NodeCell getPoint(String descriptor) {
        Scanner local_scanner = new Scanner(System.in);
        boolean valid = false;
        Integer wid = 0, hei = 0;
        System.out.println("start wall description routine");
        while (!valid) {
            try {
                System.out.println("input width for " + descriptor);
                wid = local_scanner.nextInt();
                if (wid < 0 || wid >= this.width) {
                    throw new InvalidParameterException("between 0 and " + this.width);
                }
                valid = true;
            } catch (InputMismatchException e) {
                System.out.println("only digits");
            } catch (InvalidParameterException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
        valid = false;
        while (!valid) {
            try {
                System.out.println("input height for " + descriptor);
                hei = local_scanner.nextInt();
                if (hei < 0 || hei >= this.width) {
                    throw new InvalidParameterException("between 0 and " + this.height);
                }
                valid = true;
            } catch (InputMismatchException e) {
                System.out.println("only digits");
            } catch (InvalidParameterException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
        System.out.println("wall description routine complete");
        return this.body[wid][hei];

    }

    public NodeMap(Integer width, Integer height) throws InvalidParameterException {
        /* в конструкторе проставляем точки входа-выхода, стены */
        if ((width == null || width <= 2) || (height == null || height <= 0)) {
            throw new InvalidParameterException("Я всё понимаю, но описывать сетку для"
                    + " одной ячейки/ одного столбца / одной строки"
                    + " мне как-то лениво :(");
        }
        this.height = height;
        this.width = width;
        this.wallsAreSet = false;
        this.body = new NodeCell[width][height];
        this.pathIsSet = false;
        contentDeclare();
        setWalls();
        setRouteLimits();
        setGrid();
        contentColorize();

    }

    private void setWalls() {
        /* проставляем стены */
        Scanner locScanner = new Scanner(System.in);
        while (!this.wallsAreSet) {
            System.out.println("\n####\ninput 1 for more untraversable nodes, other inputs stop routine");
            String more = locScanner.nextLine();
            if (more.equals("1")) {
                NodeCell tmp = getPoint("next wall point");
                tmp.color = EColor.RED;
                tmp.value = this.const_wall;

            } else {
                this.wallsAreSet = true;
            }
        }

    }

    private void setRouteLimits() {
        Scanner locScanner = new Scanner(System.in);
        System.out.println("\n###\nDeclare start and end points of path");
        this.start = getPoint("start point");
        this.start.color = EColor.GREEN;
        this.start.value = this.const_start;
        this.finish = getPoint("end point");
        this.finish.color = EColor.GREEN;
        this.finish.value = this.const_end;
    }

    private void contentDeclare() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                this.body[i][j] = new NodeCell(this.const_blank);
                this.body[i][j].color = EColor.WHITE;
            }

        }
        /* тут заполняем поле пустыми нодами, ставим стены и точки входа-выхода */

    }

    private boolean calculatePath() {
        return true;
    }

    private void contentColorize() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (this.body[i][j].validpath) {
                    this.body[i][j].color = EColor.BLUE;
                }
            }
        }
    }

    private void setGrid() {
        /* тут проставляем связи между соседями */
        // #region углы
        // верх лево
        if (this.body[0][0].value != this.const_wall) {

            this.body[0][0].B_NC = this.body[1][0].value != this.const_wall ? this.body[1][0] : null;
            this.body[0][0].R_NC = this.body[0][1].value != this.const_wall ? this.body[0][1] : null;
        }
        // низ лево
        if (this.body[this.height - 1][0].value != this.const_wall) {

            this.body[this.height - 1][0].R_NC = this.body[this.height - 1][1].value != this.const_wall
                    ? this.body[this.height - 1][1]
                    : null;
            this.body[this.height - 1][0].U_NC = this.body[this.height - 1][0].value != this.const_wall
                    ? this.body[this.height - 1][0]
                    : null;
        }
        // верх право
        if (this.body[0][this.width - 1].value != this.const_wall) {
            this.body[0][this.width - 1].L_NC = this.body[0][this.width - 2].value != this.const_wall
                    ? this.body[0][this.width - 2]
                    : null;
            this.body[0][this.width - 1].B_NC = this.body[1][this.width - 1].value != this.const_wall
                    ? this.body[1][this.width - 1]
                    : null;
        }
        // низ право
        if (this.body[this.height - 1][this.width - 1].value != this.const_wall) {
            this.body[this.height - 1][this.width
                    - 1].L_NC = this.body[this.height - 1][this.width - 2].value != this.const_wall
                            ? this.body[this.height - 1][this.width - 2]
                            : null;
            this.body[this.height - 1][this.width
                    - 1].U_NC = this.body[this.height - 2][this.width - 1].value != this.const_wall
                            ? this.body[this.height - 2][this.width - 1]
                            : null;
        }

        // #endregion углы

        // #region верхний край
        for (int i = 1; i < this.width - 1; i++) {
            if (this.body[0][i].value != this.const_wall) {
                this.body[0][i].B_NC = this.body[1][i].value != this.const_wall ? this.body[1][i] : null;
                this.body[0][i].L_NC = this.body[0][i - 1].value != this.const_wall ? this.body[0][i - 1] : null;
                this.body[0][i].R_NC = this.body[0][i + 1].value != this.const_wall ? this.body[0][i + 1] : null;
            }

        }
        // #endregion верхний край

        // #region нижний край
        for (int i = 1; i < this.width - 1; i++) {
            if (this.body[this.height-2][i].value != this.const_wall) {
                this.body[this.height-2][i].U_NC = this.body[this.height-3][i].value != this.const_wall ? this.body[this.height-3][i] : null;
                this.body[this.height-2][i].L_NC = this.body[this.height-2][i-1].value != this.const_wall ? this.body[this.height-2][i-1] : null;
                this.body[this.height-2][i].R_NC = this.body[this.height-2][i+1].value != this.const_wall ? this.body[this.height-2][i+1] : null;
            }

        }
        // #endregion нижний край

        // #region левый край
        for (int i = 1; i < this.height-1; i++) {
            if (this.body[i][0].value != this.const_wall) {
                this.body[i][0].U_NC = this.body[i-1][0].value != this.const_wall ? this.body[i-1][0]:null;
                this.body[i][0].B_NC = this.body[i+1][0].value != this.const_wall ? this.body[i+1][0]:null;
                this.body[i][0].R_NC = this.body[i][1].value != this.const_wall ? this.body[i][1]:null;
            }
        }
    
        // #endregion левый край

        // #region правый край
        for (int i = 1; i < this.height-1; i++) {
            if (this.body[i][this.width-1].value != this.const_wall) {
                this.body[i][this.width-1].U_NC = this.body[i-1][this.width-1].value != this.const_wall ? this.body[i-1][this.width-1]:null;
                this.body[i][this.width-1].B_NC = this.body[i+1][this.width-1].value != this.const_wall ? this.body[i+1][this.width-1]:null;
                this.body[i][this.width-1].L_NC = this.body[i][this.width-2].value != this.const_wall ? this.body[i][this.width-2]:null;
            }
        }
        // #endregion правый край
        //#region мид
        for (int i = 0; i < body.length; i++) {
            for (int j = 0; j < body.length; j++) {
                
            }
        }
        //#endregion мид
        NodeCell temp = this.body[0][0];
        System.out.println("debug");

    }

    public void Show() {
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                System.out.print(this.body[i][j] + " ");
            }
            System.out.println();
        }
    }
}