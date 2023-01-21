defmodule Day01 do
  def trimmed_line_stream(path) do
    File.stream!(path)
    |> Stream.map(&String.trim_trailing/1)
  end

  def group_between_value(enum, val) do
    case Enum.split_while(enum, &(&1 != val)) do
      {group, []} -> [group]
      {group, rest} -> [group | group_between_value(Enum.drop(rest, 1), val)]
    end
  end

  def sum_group(group) do
    Enum.map(group, fn s ->
      {n, ""} = Integer.parse(s)
      n
    end)
    |> Enum.sum()
  end

  def load_sums(path) do
    lines = trimmed_line_stream(path)
    groups = group_between_value(lines, "")
    Enum.map(groups, &sum_group/1)
  end

  def part_one_solution(path) do
    load_sums(path) |> Enum.max()
  end

  def part_two_solution(path) do
    load_sums(path) |> Enum.sort(:desc) |> Enum.take(3) |> Enum.sum()
  end
end

IO.inspect(Day01.part_one_solution("../input/day01.txt"))
IO.inspect(Day01.part_two_solution("../input/day01.txt"))
