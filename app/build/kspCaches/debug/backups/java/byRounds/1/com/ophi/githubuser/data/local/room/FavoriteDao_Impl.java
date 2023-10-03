package com.ophi.githubuser.data.local.room;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.ophi.githubuser.data.local.entity.FavoriteUser;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class FavoriteDao_Impl implements FavoriteDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<FavoriteUser> __insertionAdapterOfFavoriteUser;

  private final EntityDeletionOrUpdateAdapter<FavoriteUser> __deletionAdapterOfFavoriteUser;

  public FavoriteDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFavoriteUser = new EntityInsertionAdapter<FavoriteUser>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `FavoriteUser` (`username`,`avatarUrl`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, FavoriteUser value) {
        if (value.getUsername() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getUsername());
        }
        if (value.getAvatarUrl() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getAvatarUrl());
        }
      }
    };
    this.__deletionAdapterOfFavoriteUser = new EntityDeletionOrUpdateAdapter<FavoriteUser>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `FavoriteUser` WHERE `username` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, FavoriteUser value) {
        if (value.getUsername() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getUsername());
        }
      }
    };
  }

  @Override
  public void insert(final FavoriteUser favorite) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfFavoriteUser.insert(favorite);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final FavoriteUser favorite) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfFavoriteUser.handle(favorite);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<FavoriteUser>> getFavorite() {
    final String _sql = "SELECT * FROM favoriteUser ORDER BY username ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"favoriteUser"}, false, new Callable<List<FavoriteUser>>() {
      @Override
      public List<FavoriteUser> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfAvatarUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "avatarUrl");
          final List<FavoriteUser> _result = new ArrayList<FavoriteUser>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final FavoriteUser _item;
            final String _tmpUsername;
            if (_cursor.isNull(_cursorIndexOfUsername)) {
              _tmpUsername = null;
            } else {
              _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            }
            final String _tmpAvatarUrl;
            if (_cursor.isNull(_cursorIndexOfAvatarUrl)) {
              _tmpAvatarUrl = null;
            } else {
              _tmpAvatarUrl = _cursor.getString(_cursorIndexOfAvatarUrl);
            }
            _item = new FavoriteUser(_tmpUsername,_tmpAvatarUrl);
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
  public LiveData<FavoriteUser> getFavoriteUserByUsername(final String username) {
    final String _sql = "SELECT * FROM FavoriteUser WHERE username = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (username == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, username);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"FavoriteUser"}, false, new Callable<FavoriteUser>() {
      @Override
      public FavoriteUser call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUsername = CursorUtil.getColumnIndexOrThrow(_cursor, "username");
          final int _cursorIndexOfAvatarUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "avatarUrl");
          final FavoriteUser _result;
          if(_cursor.moveToFirst()) {
            final String _tmpUsername;
            if (_cursor.isNull(_cursorIndexOfUsername)) {
              _tmpUsername = null;
            } else {
              _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
            }
            final String _tmpAvatarUrl;
            if (_cursor.isNull(_cursorIndexOfAvatarUrl)) {
              _tmpAvatarUrl = null;
            } else {
              _tmpAvatarUrl = _cursor.getString(_cursorIndexOfAvatarUrl);
            }
            _result = new FavoriteUser(_tmpUsername,_tmpAvatarUrl);
          } else {
            _result = null;
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

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
