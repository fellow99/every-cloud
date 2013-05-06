package test.vfs.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import org.apache.commons.vfs2.FileContent;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.FileUtil;
import org.apache.commons.vfs2.Selectors;

import com.fellow.every.exception.ServerException;

public abstract class AbstractShellVFS2 {

    private final FileSystemManager mgr;
    private FileObject cwd;
    private BufferedReader reader;


    protected AbstractShellVFS2(InputStream in, FileSystemManager mgr) throws FileSystemException {
    	this.mgr = mgr;
        this.reader = new BufferedReader(new InputStreamReader(in));
    }
    
    protected FileSystemManager getFileSystemManager(){
    	return mgr;
    }
    
    protected void go() throws Exception
    {
        while (true) {
			final String[] cmd = nextCommand();
			if (cmd == null) {
				return;
			}
			if (cmd.length == 0) {
				continue;
			}
			final String cmdName = cmd[0];
			if (cmdName.equalsIgnoreCase("exit")
					|| cmdName.equalsIgnoreCase("quit")) {
				return;
			}

            try {
                handleCommand(cmd);
            } catch (final Exception e){
            	Throwable cause = e;
            	while(true){
            		if(cause == null)break;
            		if(cause instanceof ServerException){
            			ServerException se = (ServerException)cause;
                        System.err.println("Server exception:");
                    	System.err.println("CODE: " + se.getCode() + " " + se.getMessage());
                    	System.err.println("URL: " + se.getUrl());
                    	System.err.println("RESPONSE: " + se.getInfo());
                    	break;
            		}
            		cause = cause.getCause();
            	}
                System.err.println("Command failed:");
                e.printStackTrace(System.err);
            }
        }
    }

    /**
     * Handles a command.
     */
    private void handleCommand(final String[] cmd) throws Exception
    {
        final String cmdName = cmd[0];
        if (cmdName.equalsIgnoreCase("cat"))
        {
            cat(cmd);
        }
        else if (cmdName.equalsIgnoreCase("cd"))
        {
            cd(cmd);
        }
        else if (cmdName.equalsIgnoreCase("mkdir"))
        {
        	mkdir(cmd);
        }
        else if (cmdName.equalsIgnoreCase("cp"))
        {
            cp(cmd);
        }
        else if (cmdName.equalsIgnoreCase("mv"))
        {
            mv(cmd);
        }
        else if (cmdName.equalsIgnoreCase("help"))
        {
            help();
        }
        else if (cmdName.equalsIgnoreCase("ls"))
        {
            ls(cmd);
        }
        else if (cmdName.equalsIgnoreCase("pwd"))
        {
            pwd();
        }
        else if (cmdName.equalsIgnoreCase("rm"))
        {
            rm(cmd);
        }
        else if (cmdName.equalsIgnoreCase("touch"))
        {
            touch(cmd);
        }
        else
        {
            System.err.println("Unknown command \"" + cmdName + "\".");
        }
    }

    /**
     * Does a 'help' command.
     */
    private void help()
    {
        System.out.println("Commands:");
        System.out.println("help               Shows this message.");
        System.out.println("pwd                Displays current folder.");
        System.out.println("cd [folder]        Changes current folder.");
        System.out.println("ls [-R] [path]     Lists contents of a file or folder.");
        System.out.println("mkdir <folder>        Creates a folder.");
        System.out.println("cp <src> <dest>    Copies a file or folder.");
        System.out.println("mv <src> <dest>    Copies a file or folder.");
        System.out.println("rm <path>          Deletes a file or folder.");
        System.out.println("touch <path>       Sets the last-modified time of a file.");
        System.out.println("cat <file>         Displays the contents of a file.");
        System.out.println("exit       Exits this program.");
        System.out.println("quit       Exits this program.");
    }
    
    /**
     * Does an 'rm' command.
     */
    private void rm(final String[] cmd) throws Exception
    {
        if (cmd.length < 2)
        {
            throw new Exception("USAGE: rm <path>");
        }

        final FileObject file = mgr.resolveFile(cwd, cmd[1]);
        file.delete(Selectors.SELECT_SELF);
    }

    /**
     * Does a 'cp' command.
     */
    private void cp(final String[] cmd) throws Exception
    {
        if (cmd.length < 3)
        {
            throw new Exception("USAGE: cp <src> <dest>");
        }

        final FileObject src = mgr.resolveFile(cwd, cmd[1]);
        FileObject dest = mgr.resolveFile(cwd, cmd[2]);
        if (dest.exists() && dest.getType() == FileType.FOLDER)
        {
            dest = dest.resolveFile(src.getName().getBaseName());
        }

        dest.copyFrom(src, Selectors.SELECT_ALL);
    }

    /**
     * Does a 'mv' command.
     */
    private void mv(final String[] cmd) throws Exception
    {
        if (cmd.length < 3)
        {
            throw new Exception("USAGE: cp <src> <dest>");
        }

        final FileObject src = mgr.resolveFile(cwd, cmd[1]);
        FileObject dest = mgr.resolveFile(cwd, cmd[2]);
        if (dest.exists() && dest.getType() == FileType.FOLDER)
        {
            dest = dest.resolveFile(src.getName().getBaseName());
        }

        dest.moveTo(src);
    }

    /**
     * Does a 'cat' command.
     */
    private void cat(final String[] cmd) throws Exception
    {
        if (cmd.length < 2)
        {
            throw new Exception("USAGE: cat <path>");
        }

        // Locate the file
        final FileObject file = mgr.resolveFile(cwd, cmd[1]);

        // Dump the contents to System.out
        FileUtil.writeContent(file, System.out);
        System.out.println();
    }

    /**
     * Does a 'pwd' command.
     */
    private void pwd()
    {
        System.out.println("Current folder is " + cwd.getName());
    }

    /**
     * Does a 'cd' command.
     * If the taget directory does not exist, a message is printed to <code>System.err</code>.
     */
    private void cd(final String[] cmd) throws Exception
    {
        final String path;
        if (cmd.length > 1)
        {
            path = cmd[1];
        }
        else
        {
            path = System.getProperty("user.home");
        }

        // Locate and validate the folder
        FileObject tmp = mgr.resolveFile(cwd, path);
        if (tmp.exists())
        {
            cwd = tmp;
        }
        else
        {
            System.out.println("Folder does not exist: " + tmp.getName());
        }
        System.out.println("Current folder is " + cwd.getName());
    }

    /**
     * Does an 'ls' command.
     */
    private void ls(final String[] cmd) throws FileSystemException
    {
        int pos = 1;
        final boolean recursive;
        if (cmd.length > pos && cmd[pos].equals("-R"))
        {
            recursive = true;
            pos++;
        }
        else
        {
            recursive = false;
        }

        final FileObject file;
        if (cmd.length > pos)
        {
            file = mgr.resolveFile(cwd, cmd[pos]);
        }
        else
        {
            file = cwd;
        }

        if (file.getType() == FileType.FOLDER)
        {
            // List the contents
            System.out.println("Contents of " + file.getName());
            listChildren(file, recursive, "");
        }
        else
        {
            // Stat the file
            System.out.println(file.getName());
            final FileContent content = file.getContent();
            System.out.println("Size: " + content.getSize() + " bytes.");
            final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
            final String lastMod = dateFormat.format(new Date(content.getLastModifiedTime()));
            System.out.println("Last modified: " + lastMod);
        }
    }

    /**
     * Does a 'touch' command.
     */
    private void touch(final String[] cmd) throws Exception
    {
        if (cmd.length < 2)
        {
            throw new Exception("USAGE: touch <path>");
        }
        final FileObject file = mgr.resolveFile(cwd, cmd[1]);
        if (!file.exists())
        {
            file.createFile();
        }
        file.getContent().setLastModifiedTime(System.currentTimeMillis());
    }


    private void mkdir(final String[] cmd) throws Exception
    {
        if (cmd.length < 2)
        {
            throw new Exception("USAGE: mkdir <folder>");
        }
        cwd.resolveFile(cmd[1]).createFolder();
    }

    /**
     * Lists the children of a folder.
     */
    private void listChildren(final FileObject dir,
                              final boolean recursive,
                              final String prefix)
        throws FileSystemException
    {
        final FileObject[] children = dir.getChildren();
        for (int i = 0; i < children.length; i++)
        {
            final FileObject child = children[i];
            System.out.print(prefix);
            System.out.print(child.getName().getBaseName());
            if (child.getType() == FileType.FOLDER)
            {
                System.out.println("/");
                if (recursive)
                {
                    listChildren(child, recursive, prefix + "    ");
                }
            }
            else
            {
                System.out.println();
            }
        }
    }

    /**
     * Returns the next command, split into tokens.
     */
    private String[] nextCommand() throws IOException
    {
        System.out.print("> ");
        final String line = reader.readLine();
        if (line == null)
        {
            return null;
        }
        final ArrayList cmd = new ArrayList();
        final StringTokenizer tokens = new StringTokenizer(line);
        while (tokens.hasMoreTokens())
        {
            cmd.add(tokens.nextToken());
        }
        return (String[]) cmd.toArray(new String[cmd.size()]);
    }
}
