require_relative "graph.rb"

class Synsets
  attr_accessor :hash
  def initialize
    @hash = {} #to store
  end

  def load(synsets_file)
    line_num = 1
    invaild_num = [] #array of invalid line numbers (indexed from 1)
    #should be returned in ascending order.
    hash_temp = {}

    File.open(synsets_file, "r") do |f|
      f.each_line do |line|
        if line.match(/^id: ([\d]+) synset: ([A-Za-z0-9\-_.'\/,]+)$/) then
          #for separating id (words separated by white spaces)
          id, words = $1, $2.split(",") #$2 to store all the nouns
          #array with each noun

          #store id and synset in hash
          if @hash.has_key? id.to_i
            invaild_num.push(line_num)
          else
            hash_temp[id.to_i] = words
          end
        #line invaild case(line_number in ascending order)
        else
          invaild_num.push(line_num)
        end #end if line.match
        line_num += 1
      end #line loop end
    end #end file

    if invaild_num.empty?
      hash_temp.each do |key, value|
        addSet(key, value)
      end
      return nil
    else
      return invaild_num
    end
  end

#given a non-negative id, an array of nouns
  def addSet(synset_id, nouns)
    #if id < 0, nouns is empty or id has been defined = false
      if synset_id < 0 or nouns == [] or @hash.has_key?(synset_id)
        return false
      end
     #otherwise, return true and store the new synset
      @hash[synset_id] = nouns
      return true
  end

  def lookup(synset_id)
    #if there is no entry for the requested id, return an empty array
    if @hash.has_key? synset_id
      return @hash[synset_id]
    else
      return []
    end
  end
  #behave differently depending on if it is given a String or
  #an Array as its argument.
  def findSynsets(to_find)
    arry = [] #when to_find is a string
    res_hash = {}
    res = @hash.values

    #return an array of 0 or more synset ids corresponding to synsets
    #containing this noun.
    if to_find.is_a? String
      arry = @hash.select{|k, v| v.include? to_find}.keys
      arry
    #each key is a single noun from the array
    #each value is an array of synset ids
    elsif to_find.kind_of? Array
      to_find.each do |noun|
        res_hash[noun] = @hash.select {|k, v| v.include? noun}.keys
      end
      res_hash
    else
      nil
    end
  end
end
# If anything but a String or an Array is provided  return nil.

class Hypernyms
  attr_accessor :graph
  def initialize
    @graph = Graph.new #to store
  end

  def load(hypernyms_file)
    line_num = 1
    invaild_num = [] #array of invalid line numbers (indexed from 1)
    #should be returned in ascending order.

    File.open(hypernyms_file, "r") do |f|
      f.each_line do |line|
        if !line.match(/^from: (\d+) to: ([\d,]+)$/) then
          invaild_num.push(line_num)
        end #end if
        line_num += 1
      end #end line loop
    end #end file.open loop

    if invaild_num.empty?
      File.open(hypernyms_file, "r") do |f|
        f.each_line do |line|
          if line.match(/^from: (\d+) to: ([\d,]+)$/) then
            from, to = $1.to_i, $2.split(",")

            to.each do |num|
              addHypernym(from, num.to_i)
            end
          end
        end
        return nil
      end
    else
      return invaild_num
    end
  end

  def addHypernym(source, destination)
    if source < 0 or destination < 0 or source == destination
      return false
    end
    if @graph.hasEdge? source, destination
      return true
    end
    if !@graph.hasVertex? source
      @graph.addVertex(source)
    end
    if !@graph.hasVertex? destination
      @graph.addVertex(destination)
    end
      @graph.addEdge(source, destination)
      return true
  end
  def lca(id1, id2)
    sap = -1
    arr = [] #to store lowest common ancestors
    one = []
    if !@graph.hasVertex? id1 or !@graph.hasVertex? id2
      return nil
    end
    lca_hash = @graph.bfs(id2)
    @graph.bfs(id1).each_pair{|org, dis| #org = key, dis = dis btwn
    if lca_hash.has_key? org
      curr = dis + lca_hash[org] #distance
      arr << [org, curr] #from origin to the current position
      if curr < sap || sap == -1
        sap = curr
      end
    end # end if
    }
    # If no common ancestor is found, then an empty array should be returned
    if arr.empty?
      return arr
    end
    arr.each { |num|
      if num[1] == sap
        one.push(num[0])
      end
}
    one
  end
end

class CommandParser
  def initialize
    @synsets = Synsets.new
    @hypernyms = Hypernyms.new
  end

  def parse(command)
    parse = {}
    cmd = command.split(" ")
    result = true

    if cmd[0] == "load"
      if cmd[1].match(/^\s*([\w+\.\/\-]*)\s*([\w+\.\/\-]*)\s*$/) then
        if !@synsets.load(cmd[1]) == nil or !@hypernyms.load(cmd[2]) == nil
          result = false
          parse = {:recognized_command => :load, :result => :error}
        else
          parse = {:recognized_command => :load, :result => result}
        end
      end
    elsif cmd[0] == "lookup"
      if cmd[1].match(/^(\d+)$/) then
        result = @synsets.lookup($1.to_i)
        parse = {:recognized_command => :lookup, :result => result}
      else
        parse = {:recognized_command => :lookup, :result => :error}
      end
    elsif cmd[0] == "find"
      if cmd[1].match(/^(\w+)$/) then
        result = @synsets.findSynsets($1)
        parse = {:recognized_command => :find, :result => result}
      else
        parse = {:recognized_command => :find, :result => :error}
      end
    elsif cmd[0] == "findmany"
      if cmd[1].match(/^[(\w+),]*$/) then
        split = cmd[1].split(",")
        result = @synsets.findSynsets(split)
        puts result
        parse = {:recognized_command => :findmany, :result => result}
      else
        parse = {:recognized_command => :findmany, :result => :error}
      end
    elsif cmd[0] == "lca"
      if cmd[1].match(/^(\d+)\s*(\d+)$/) then
        result = @hypernyms.lca($1.to_i, $2.to_i)
        parse = {:recognized_command => :lca, :result => result}
      else
        parse = {:recognized_command => :lca, :result => :error}
      end
    else
      parse = {:recognized_command => :invalid}
    end
    parse
  end
end
