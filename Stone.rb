# -*- coding: utf-8 -*-
require 'pp'

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
    island
  end

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

  private
  def traversable?(y, x, visited)
    inside?(y, x) && !(visited.include?([y,x])) && @map[y][x] == ISLAND
  end

  private
  def inside?(y, x)
    (0 <= y && y < @y) && (0 <= x && x < @x)
  end
end

s = Stone.new

## for debug
pp s.map
pp s.count_island
