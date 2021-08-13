package simulation;

import java.util.*;

public class 프렌즈4블록 {
    Queue<Node> bombQueue = new LinkedList<>();

    int row;
    int col;
    boolean isFinish = false;
    int[] dy = new int[]{0, 1, 1}; // 01  우, 하, 대각선아래
    int[] dx = new int[]{1, 0, 1}; // 11
    char[][] blocks;

    private class Node {
        private int r;
        private int c;
        private char block;

        public Node(int r, int c, char block) {
            this.r = r;
            this.c = c;
            this.block = block;
        }
    }

    private void dropBlocks() {
        isFinish = true;

        for (int j = 0; j < col; j++) {
            for (int i = row - 1; i >= 0; i--) {
                if (blocks[i][j] != '-') continue;

                // 빈 블럭이 아닌 블럭 찾기
                int targetRow = i - 1;

                while (targetRow >= 0 && blocks[targetRow][j] == '-') {
                    targetRow--;
                }

                if (targetRow < 0) continue;
                blocks[i][j] = blocks[targetRow][j];
                blocks[targetRow][j] = '-';
                isFinish = false;
            }
        }
    }

    private void bombBlocks() {
        while (!bombQueue.isEmpty()) {
            Node bomb = bombQueue.poll();

            if (bomb.block != '-') blocks[bomb.r][bomb.c] = '-';
        }
    }

    private boolean isRange (int nr, int nc) {
        return nr >= 0 && nr < row && nc >= 0 && nc < col;
    }

    private void offerBombBlocks(int i, int j) {
        bombQueue.offer(new Node(i, j, blocks[i][j]));
        for (int k = 0; k < 3; k++) {
            int nr = dy[k] + i;
            int nc = dx[k] + j;
            bombQueue.offer(new Node(nr, nc, blocks[i][j]));
        }
    }

    private int countSameBlocks(int i, int j) {
        int cnt = 0;
        for (int k = 0; k < 3; k++) {
            int nr = dy[k] + i;
            int nc = dx[k] + j;

            if (!isRange(nr, nc)) continue;
            if (blocks[i][j] == blocks[nr][nc]) cnt++;
        }
        return cnt;
    }

    private int countSpaceBlocks() {
        int count = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (blocks[i][j] == '-') count++;
            }
        }
        return count;
    }

    private void print(int m, int n) {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(blocks[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int solution(int m, int n, String[] board) {
        row = m;
        col = n;

        blocks = new char[m][n];
        for (int i = 0; i < row; i++) {
            blocks[i] = board[i].toCharArray();
        }

        while (!isFinish) {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    // 빈 블럭이 아니고, 2 * 2 정사각형이 다 동일한 블럭이면 큐(bombQueue)에 넣기
                    if ((blocks[i][j] != '-') && (countSameBlocks(i, j) == 3)) offerBombBlocks(i, j);
                }
            }

            bombBlocks(); // bombQueue에 들어있는 블럭을 빈 블럭으로 변경
            dropBlocks(); // 블럭 떨어뜨리기
        }

        return countSpaceBlocks();
    }
}
