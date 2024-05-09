using Lab7.Writers;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.ConstrainedExecution;
using System.Text.Json;
using System.Text;
using System.Threading.Tasks;

namespace Lab7
{
    internal class FileSystemPrinter
    {
        readonly string path;
        readonly IWriter writer;

        static readonly char ELBOW_CHAR = '└';
        static readonly char JOIN_CHAR = '├';
        static readonly char HORIZONTAL_CHAR = '─';
        static readonly char VERTICAL_CHAR = '│';
        static readonly int JOINING_LINE_LENGTH = 3;

        public FileSystemPrinter(string path, IWriter writer)
        {
            this.path = path;
            this.writer = writer;
        }

        public void PrintFilesRecursively()
        {
            DirectoryInfo currentDirectoryInfo = new DirectoryInfo(path);
            if (!currentDirectoryInfo.Exists)
            {
                Console.WriteLine($"Directory {path} does not exist");
                return;
            }
            PrintFilesRecursivelyWithDepth(path, "", 0);
        }

        private void PrintFilesRecursivelyWithDepth(string path, string indentation, int nthDirectoryInParentDirectory)
        {
            DirectoryInfo currDirInfo = new DirectoryInfo(path);

            bool isLastDir = isTheLastDirectory(nthDirectoryInParentDirectory, currDirInfo.Parent);
            string dirIndentation = indentation + (isLastDir ? ELBOW_CHAR : JOIN_CHAR) + new string(HORIZONTAL_CHAR, JOINING_LINE_LENGTH);
            string fileIndentation = indentation + (isLastDir ? ' ' : VERTICAL_CHAR) + new string(' ', JOINING_LINE_LENGTH);

            StringBuilder dirLine = new StringBuilder()
                .Append(dirIndentation)
                .Append($" [ {currDirInfo.Name} ] ")
                .Append($"( {GetDirectoryElementsCount(currDirInfo)} ) ");
            writer.Write(dirLine.ToString());

            FileInfo[] dirFiles = currDirInfo.GetFiles();
            for (int i = 0; i < dirFiles.Length; i++)
            {
                writer.Write(CreateFileLine(dirFiles[i], i, currDirInfo, fileIndentation));
            }

            DirectoryInfo[] dirDirs = currDirInfo.GetDirectories();
            for (int i = 0; i < dirDirs.Length; i++)
            {
                DirectoryInfo dir = dirDirs[i];
                if (isTheLastDirectory(i, currDirInfo))
                {
                    int indexOfLastIndent = fileIndentation.LastIndexOf(JOIN_CHAR + new string(HORIZONTAL_CHAR, JOINING_LINE_LENGTH));
                    if (indexOfLastIndent != -1)
                    {
                        fileIndentation = fileIndentation.Substring(0, indexOfLastIndent);
                        fileIndentation += new string(' ', JOINING_LINE_LENGTH + 1);
                    }
                }
                PrintFilesRecursivelyWithDepth(dir.FullName, fileIndentation, i);
            }
        }

        private string CreateFileLine(FileInfo file, int fileIndex, DirectoryInfo currDirInfo, string fileIndentation)
        {
            StringBuilder newFileIndentation = new StringBuilder()
                   .Append(fileIndentation)
                   .Append(GetJoiningChar(fileIndex, GetDirectoryElementsCount(currDirInfo)))
                   .Append(new string(HORIZONTAL_CHAR, JOINING_LINE_LENGTH));
            StringBuilder fileLine = new StringBuilder()
                .Append(newFileIndentation)
                .Append(" | ")
                .Append(file.Name)
                .Append($" | {file.Length}b, ")
                .Append(file.GetRahsAttributes());

            return fileLine.ToString();
        }

        private char GetJoiningChar(int currentIndex, int totalLength)
        {
            return currentIndex == totalLength - 1 ? ELBOW_CHAR : JOIN_CHAR;
        }

        private int GetDirectoryElementsCount(DirectoryInfo dirInfo)
        {
            return dirInfo.GetDirectories().Length + dirInfo.GetFiles().Length;    
        }

        private bool isTheLastDirectory(int index, DirectoryInfo dir)
        {
            return index == dir.GetDirectories().Length - 1;
        }

    }
}
