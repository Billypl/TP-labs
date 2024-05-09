using Lab7.Writers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;

namespace Lab7
{
    static public class MyExtensions
    {
        public static DateTime FindOldestFile(this DirectoryInfo directoryInfo)
        {
            DateTime oldestCreationTime = DateTime.MaxValue;
            FileInfo? oldestFile = directoryInfo
                .GetFiles()
                .OrderBy(directoryInfo => directoryInfo.CreationTime)
                .FirstOrDefault();
            if (oldestFile != null)
            {
                oldestCreationTime = oldestFile.CreationTime;
            }
            return oldestCreationTime;
        }

        public static string GetRahsAttributes(this FileSystemInfo fileSystemInfo)
        {
            StringBuilder attributes = new StringBuilder();
            attributes.Append(fileSystemInfo.Attributes.HasFlag(FileAttributes.ReadOnly) ? "R" : "-");
            attributes.Append(fileSystemInfo.Attributes.HasFlag(FileAttributes.Archive) ? "A" : "-");
            attributes.Append(fileSystemInfo.Attributes.HasFlag(FileAttributes.Hidden) ? "H" : "-");
            attributes.Append(fileSystemInfo.Attributes.HasFlag(FileAttributes.System) ? "S" : "-");
            return attributes.ToString();
        }

        public static SortedDictionary<string, int> GetFilesAsSortedDict(this DirectoryInfo directoryInfo)
        {
            SortedDictionary<string, int> elements = new SortedDictionary<string, int>(new StringLengthComparer());
            directoryInfo.GetFiles().ToList().ForEach(fileInfo => elements.Add(fileInfo.Name, (int)fileInfo.Length));
            directoryInfo.GetDirectories().ToList().ForEach(dirInfo => elements.Add(dirInfo.Name, (int)(dirInfo.GetDirectories().Length + directoryInfo.GetFiles().Length)));
            return elements;
        }

    }
}
