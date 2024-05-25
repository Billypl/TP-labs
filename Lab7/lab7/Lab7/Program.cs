using Lab7;
using Lab7.Writers;
using System;
using System.IO;
using System.Text.Json;

class Program
{

    static void Main()
    {
        System.Console.BackgroundColor = ConsoleColor.Black;

        string path = "C:\\Users\\kripe\\Desktop\\Uczelnia\\sem 4\\PT\\PT Lab\\Lab7";
        IWriter writer = new ConsoleWriter();
        FileSystemPrinter printer = new FileSystemPrinter(path, writer);

        writer.Write("\n--------------------------- \nFiles reqursively: ");
        printer.PrintFilesRecursively();

        writer.Write("\n--------------------------- \nSorted files:");
        DirectoryInfo directoryInfo = new DirectoryInfo(path);
        SortedDictionary<string, int> elements = directoryInfo.GetFilesAsSortedDict();
        elements.ToList().ForEach(element => writer.Write($"name: {element.Key} size: {element.Value}"));

        writer.Write("\n--------------------------- \nOldest file:");
        writer.Write(directoryInfo.FindOldestFile().ToString());
        string jsonString = JsonSerializer.Serialize(elements);

        writer.Write("\n--------------------------- \nSerialized files:");
        writer.Write(jsonString);
        writer.Write("\n--------------------------- \nDeserialized files:");
        SortedDictionary<string, int> elementsDeserialized = JsonSerializer.Deserialize<SortedDictionary<string, int>>(jsonString);
        elementsDeserialized.ToList().ForEach(element => writer.Write($"name: {element.Key} size: {element.Value}"));
    }
}



