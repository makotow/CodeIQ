# -*- coding: utf-8 -*-

require 'pp' # for test

class Stone
  SEA = 0
  ISLAND = 1

  attr_accessor :map

  def initialize x=6, y=6, max=4
    @x = x
    @y = y
    @max = max
    @map = @y.times.map{[0] * @x}

    count = 0
    while count < @max
      y = rand @y - 1
      x = rand @x - 1

      if @map[y][x] == SEA
        @map[y][x] = ISLAND
        count += 1
      end
    end
  end

  #= count_island
  # @map の island の数を返す。
  #
  # @map に対してDFSを何回実施できたかでISLANDの個数を返す。
  def count_island
    visited = Array.new
    island = 0
    @map.each_with_index do | row, y |
      row.each_with_index do | e, x |
        if traversable?(y, x, visited)
          dfs(y, x, visited)
          island+=1
        end
      end
    end
    return island
  end

  #= dfs
  # (y,x) がISLANDの場合、隣接ノードを確認し、
  # ISLANDの場合、再帰的に辿り訪問済みとしてマークする。
  # 訪問済みのノードについてはDFSは実行しない。
  private
  def dfs(y, x, visited)
    dxy = [[-1,0], [0, 1], [1, 0], [0, -1]]

    dxy.each do |e|
      next_y = y + e[0]
      next_x = x + e[1]

      if traversable?(next_y, next_x, visited)
        visited << [next_y, next_x]
        dfs(next_y, next_x, visited)
      end
    end
  end

  # DFSの条件確認のユーティリティ
  # 以下の条件を満たす場合 true
  #
  # 引数 y, x の地点が
  # * @mapのサイズ内
  # * @mapで未訪問(引数のvisitedに含まれていないこと)
  # * @mapでISLAND
  private
  def traversable?(y, x, visited)
    inside?(y, x) && !(visited.include?([y,x])) && @map[y][x] == ISLAND
  end

  private
  def inside?(y, x)
    (0 <= y && y < @y) && (0 <= x && x < @x)
  end
end

## testing
s = Stone.new
pp s.map
pp s.count_island
