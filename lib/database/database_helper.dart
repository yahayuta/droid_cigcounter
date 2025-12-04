import 'package:sqflite/sqflite.dart';
import 'package:droid_cigcounter/models/cigarette_log.dart';

class DatabaseHelper {
  static final DatabaseHelper instance = DatabaseHelper._init();
  static Database? _database;

  DatabaseHelper._init();

  Future<Database> get database async {
    if (_database != null) return _database!;
    _database = await _initDB('cigcounter.db');
    return _database!;
  }

  Future<Database> _initDB(String filePath) async {
    final dbPath = await getDatabasesPath();
    final path = '$dbPath/$filePath';

    return await openDatabase(path, version: 1, onCreate: _createDB);
  }

  Future _createDB(Database db, int version) async {
    const idType = 'INTEGER PRIMARY KEY AUTOINCREMENT';
    const integerType = 'INTEGER NOT NULL';

    await db.execute('''
CREATE TABLE cigarette_logs ( 
  id $idType, 
  timestamp $integerType
  )
''');
  }

  Future<int> create(CigaretteLog log) async {
    final db = await instance.database;
    return await db.insert('cigarette_logs', log.toMap());
  }

  Future<CigaretteLog> read(int id) async {
    final db = await instance.database;
    final maps = await db.query(
      'cigarette_logs',
      columns: ['id', 'timestamp'],
      where: 'id = ?',
      whereArgs: [id],
    );

    if (maps.isNotEmpty) {
      return CigaretteLog.fromMap(maps.first);
    } else {
      throw Exception('ID $id not found');
    }
  }

  Future<List<CigaretteLog>> readAllLogs() async {
    final db = await instance.database;
    final orderBy = 'timestamp DESC';
    final result = await db.query('cigarette_logs', orderBy: orderBy);

    return result.map((json) => CigaretteLog.fromMap(json)).toList();
  }
  
  // Get logs for a specific day (start and end timestamps)
  Future<List<CigaretteLog>> readLogsByDateRange(int start, int end) async {
    final db = await instance.database;
    final result = await db.query(
      'cigarette_logs',
      where: 'timestamp >= ? AND timestamp <= ?',
      whereArgs: [start, end],
      orderBy: 'timestamp DESC',
    );
    return result.map((json) => CigaretteLog.fromMap(json)).toList();
  }

  Future<int> countLogsByDateRange(int start, int end) async {
    final db = await instance.database;
    final result = await db.rawQuery(
      'SELECT COUNT(*) FROM cigarette_logs WHERE timestamp >= ? AND timestamp <= ?',
      [start, end],
    );
    return Sqflite.firstIntValue(result) ?? 0;
  }
  
  Future<int> countAll() async {
      final db = await instance.database;
      final result = await db.rawQuery('SELECT COUNT(*) FROM cigarette_logs');
      return Sqflite.firstIntValue(result) ?? 0;
  }

  Future<int> delete(int id) async {
    final db = await instance.database;
    return await db.delete(
      'cigarette_logs',
      where: 'id = ?',
      whereArgs: [id],
    );
  }
  
  Future<int> deleteAll() async {
    final db = await instance.database;
    return await db.delete('cigarette_logs');
  }

  Future close() async {
    final db = await instance.database;
    db.close();
  }
}
