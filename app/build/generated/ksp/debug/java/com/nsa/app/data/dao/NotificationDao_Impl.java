package com.nsa.app.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.nsa.app.data.model.AppInfo;
import com.nsa.app.data.model.SavedNotification;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class NotificationDao_Impl implements NotificationDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SavedNotification> __insertionAdapterOfSavedNotification;

  private final EntityDeletionOrUpdateAdapter<SavedNotification> __deletionAdapterOfSavedNotification;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOlderThan;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public NotificationDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSavedNotification = new EntityInsertionAdapter<SavedNotification>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `notifications` (`id`,`appName`,`packageName`,`title`,`text`,`bigText`,`timestamp`,`postTime`,`isOngoing`,`category`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SavedNotification entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getAppName());
        statement.bindString(3, entity.getPackageName());
        if (entity.getTitle() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getTitle());
        }
        if (entity.getText() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getText());
        }
        if (entity.getBigText() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getBigText());
        }
        statement.bindLong(7, entity.getTimestamp());
        statement.bindLong(8, entity.getPostTime());
        final int _tmp = entity.isOngoing() ? 1 : 0;
        statement.bindLong(9, _tmp);
        if (entity.getCategory() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getCategory());
        }
      }
    };
    this.__deletionAdapterOfSavedNotification = new EntityDeletionOrUpdateAdapter<SavedNotification>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `notifications` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SavedNotification entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteOlderThan = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM notifications WHERE timestamp < ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM notifications";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final SavedNotification notification,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSavedNotification.insert(notification);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final SavedNotification notification,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfSavedNotification.handle(notification);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOlderThan(final long timestamp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOlderThan.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, timestamp);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteOlderThan.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<SavedNotification>> getAll() {
    final String _sql = "SELECT * FROM notifications ORDER BY postTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"notifications"}, new Callable<List<SavedNotification>>() {
      @Override
      @NonNull
      public List<SavedNotification> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAppName = CursorUtil.getColumnIndexOrThrow(_cursor, "appName");
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfBigText = CursorUtil.getColumnIndexOrThrow(_cursor, "bigText");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfPostTime = CursorUtil.getColumnIndexOrThrow(_cursor, "postTime");
          final int _cursorIndexOfIsOngoing = CursorUtil.getColumnIndexOrThrow(_cursor, "isOngoing");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final List<SavedNotification> _result = new ArrayList<SavedNotification>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SavedNotification _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpAppName;
            _tmpAppName = _cursor.getString(_cursorIndexOfAppName);
            final String _tmpPackageName;
            _tmpPackageName = _cursor.getString(_cursorIndexOfPackageName);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpText;
            if (_cursor.isNull(_cursorIndexOfText)) {
              _tmpText = null;
            } else {
              _tmpText = _cursor.getString(_cursorIndexOfText);
            }
            final String _tmpBigText;
            if (_cursor.isNull(_cursorIndexOfBigText)) {
              _tmpBigText = null;
            } else {
              _tmpBigText = _cursor.getString(_cursorIndexOfBigText);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final long _tmpPostTime;
            _tmpPostTime = _cursor.getLong(_cursorIndexOfPostTime);
            final boolean _tmpIsOngoing;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsOngoing);
            _tmpIsOngoing = _tmp != 0;
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            _item = new SavedNotification(_tmpId,_tmpAppName,_tmpPackageName,_tmpTitle,_tmpText,_tmpBigText,_tmpTimestamp,_tmpPostTime,_tmpIsOngoing,_tmpCategory);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getById(final long id, final Continuation<? super SavedNotification> $completion) {
    final String _sql = "SELECT * FROM notifications WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SavedNotification>() {
      @Override
      @Nullable
      public SavedNotification call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAppName = CursorUtil.getColumnIndexOrThrow(_cursor, "appName");
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfBigText = CursorUtil.getColumnIndexOrThrow(_cursor, "bigText");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfPostTime = CursorUtil.getColumnIndexOrThrow(_cursor, "postTime");
          final int _cursorIndexOfIsOngoing = CursorUtil.getColumnIndexOrThrow(_cursor, "isOngoing");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final SavedNotification _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpAppName;
            _tmpAppName = _cursor.getString(_cursorIndexOfAppName);
            final String _tmpPackageName;
            _tmpPackageName = _cursor.getString(_cursorIndexOfPackageName);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpText;
            if (_cursor.isNull(_cursorIndexOfText)) {
              _tmpText = null;
            } else {
              _tmpText = _cursor.getString(_cursorIndexOfText);
            }
            final String _tmpBigText;
            if (_cursor.isNull(_cursorIndexOfBigText)) {
              _tmpBigText = null;
            } else {
              _tmpBigText = _cursor.getString(_cursorIndexOfBigText);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final long _tmpPostTime;
            _tmpPostTime = _cursor.getLong(_cursorIndexOfPostTime);
            final boolean _tmpIsOngoing;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsOngoing);
            _tmpIsOngoing = _tmp != 0;
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            _result = new SavedNotification(_tmpId,_tmpAppName,_tmpPackageName,_tmpTitle,_tmpText,_tmpBigText,_tmpTimestamp,_tmpPostTime,_tmpIsOngoing,_tmpCategory);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<SavedNotification>> search(final String query) {
    final String _sql = "SELECT * FROM notifications WHERE (title LIKE '%' || ? || '%' OR text LIKE '%' || ? || '%') ORDER BY postTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"notifications"}, new Callable<List<SavedNotification>>() {
      @Override
      @NonNull
      public List<SavedNotification> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAppName = CursorUtil.getColumnIndexOrThrow(_cursor, "appName");
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfBigText = CursorUtil.getColumnIndexOrThrow(_cursor, "bigText");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfPostTime = CursorUtil.getColumnIndexOrThrow(_cursor, "postTime");
          final int _cursorIndexOfIsOngoing = CursorUtil.getColumnIndexOrThrow(_cursor, "isOngoing");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final List<SavedNotification> _result = new ArrayList<SavedNotification>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SavedNotification _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpAppName;
            _tmpAppName = _cursor.getString(_cursorIndexOfAppName);
            final String _tmpPackageName;
            _tmpPackageName = _cursor.getString(_cursorIndexOfPackageName);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpText;
            if (_cursor.isNull(_cursorIndexOfText)) {
              _tmpText = null;
            } else {
              _tmpText = _cursor.getString(_cursorIndexOfText);
            }
            final String _tmpBigText;
            if (_cursor.isNull(_cursorIndexOfBigText)) {
              _tmpBigText = null;
            } else {
              _tmpBigText = _cursor.getString(_cursorIndexOfBigText);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final long _tmpPostTime;
            _tmpPostTime = _cursor.getLong(_cursorIndexOfPostTime);
            final boolean _tmpIsOngoing;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsOngoing);
            _tmpIsOngoing = _tmp != 0;
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            _item = new SavedNotification(_tmpId,_tmpAppName,_tmpPackageName,_tmpTitle,_tmpText,_tmpBigText,_tmpTimestamp,_tmpPostTime,_tmpIsOngoing,_tmpCategory);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<SavedNotification>> getByPackage(final String packageName) {
    final String _sql = "SELECT * FROM notifications WHERE packageName = ? ORDER BY postTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, packageName);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"notifications"}, new Callable<List<SavedNotification>>() {
      @Override
      @NonNull
      public List<SavedNotification> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfAppName = CursorUtil.getColumnIndexOrThrow(_cursor, "appName");
          final int _cursorIndexOfPackageName = CursorUtil.getColumnIndexOrThrow(_cursor, "packageName");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfBigText = CursorUtil.getColumnIndexOrThrow(_cursor, "bigText");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfPostTime = CursorUtil.getColumnIndexOrThrow(_cursor, "postTime");
          final int _cursorIndexOfIsOngoing = CursorUtil.getColumnIndexOrThrow(_cursor, "isOngoing");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final List<SavedNotification> _result = new ArrayList<SavedNotification>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SavedNotification _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpAppName;
            _tmpAppName = _cursor.getString(_cursorIndexOfAppName);
            final String _tmpPackageName;
            _tmpPackageName = _cursor.getString(_cursorIndexOfPackageName);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpText;
            if (_cursor.isNull(_cursorIndexOfText)) {
              _tmpText = null;
            } else {
              _tmpText = _cursor.getString(_cursorIndexOfText);
            }
            final String _tmpBigText;
            if (_cursor.isNull(_cursorIndexOfBigText)) {
              _tmpBigText = null;
            } else {
              _tmpBigText = _cursor.getString(_cursorIndexOfBigText);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final long _tmpPostTime;
            _tmpPostTime = _cursor.getLong(_cursorIndexOfPostTime);
            final boolean _tmpIsOngoing;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsOngoing);
            _tmpIsOngoing = _tmp != 0;
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            _item = new SavedNotification(_tmpId,_tmpAppName,_tmpPackageName,_tmpTitle,_tmpText,_tmpBigText,_tmpTimestamp,_tmpPostTime,_tmpIsOngoing,_tmpCategory);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<AppInfo>> getDistinctApps() {
    final String _sql = "SELECT DISTINCT packageName, appName FROM notifications ORDER BY appName ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"notifications"}, new Callable<List<AppInfo>>() {
      @Override
      @NonNull
      public List<AppInfo> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPackageName = 0;
          final int _cursorIndexOfAppName = 1;
          final List<AppInfo> _result = new ArrayList<AppInfo>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AppInfo _item;
            final String _tmpPackageName;
            _tmpPackageName = _cursor.getString(_cursorIndexOfPackageName);
            final String _tmpAppName;
            _tmpAppName = _cursor.getString(_cursorIndexOfAppName);
            _item = new AppInfo(_tmpPackageName,_tmpAppName);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
