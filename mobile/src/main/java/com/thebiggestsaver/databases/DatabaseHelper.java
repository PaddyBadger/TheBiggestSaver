package com.thebiggestsaver.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import co.touchlab.android.superbus.errorcontrol.StorageException;
import co.touchlab.android.superbus.storage.CommandPersistenceProvider;
import co.touchlab.android.superbus.storage.sqlite.ClearSQLiteDatabase;

/**
 * Created by patriciaestridge on 9/6/14.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper
{
    private static final String DATABASE_FILE_NAME = "savingsrecords";
    private static final int VERSION = 1;
    private static DatabaseHelper instance;


    private DatabaseHelper(Context context)
    {
        super(context, DATABASE_FILE_NAME, null, VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new DatabaseHelper(context);
        }

        return instance;
    }

    private final Class[] tableClasses = new Class[]{
            SavingsRecord.class
    };

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try
        {
            for (Class mTableClass : tableClasses)
            {
                TableUtils.getCreateTableStatements(connectionSource, mTableClass);
                TableUtils.createTable(connectionSource, mTableClass);
            }

            CommandPersistenceProvider.createTables(new ClearSQLiteDatabase(sqLiteDatabase));
        }
        catch (StorageException e)
        {
            throw new RuntimeException(e);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onOpen(SQLiteDatabase db)
    {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }


    @Override
    public void onUpgrade(final SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion)
    {

        for (int i = tableClasses.length - 1; i >= 0; i--)
        {
            Class tableClass = tableClasses[i];
            try
            {
                TableUtils.dropTable(connectionSource, tableClass, true);
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
        }

        try
        {
            CommandPersistenceProvider.dropTables(new ClearSQLiteDatabase(sqLiteDatabase));
        }
        catch (StorageException e)
        {
            throw new RuntimeException(e);
        }

        onCreate(sqLiteDatabase, connectionSource);

    }
}
